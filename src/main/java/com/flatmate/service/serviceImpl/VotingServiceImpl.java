package com.flatmate.service.serviceImpl;

import com.flatmate.dto.TrendingVoteResponseDto;
import com.flatmate.dto.VotingRequestDto;
import com.flatmate.dto.VotingResponseDto;
import com.flatmate.entity.Complaint;
import com.flatmate.entity.User;
import com.flatmate.entity.Vote;
import com.flatmate.enums.VoteType;
import com.flatmate.exceptionhandler.customeexception.NotFoundException;
import com.flatmate.exceptionhandler.customeexception.WrongInputException;
import com.flatmate.repository.ComplaintRepo;
import com.flatmate.repository.FlatRepo;
import com.flatmate.repository.UserRepo;
import com.flatmate.repository.VoteRepo;
import com.flatmate.response.CommonResponse;
import com.flatmate.service.VotingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VotingServiceImpl implements VotingService {

    @Autowired
    private VoteRepo voteRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FlatRepo flatRepo;

    @Override
    public ResponseEntity<?> voteForComplain(VotingRequestDto votingRequestDto) {

        Complaint complaint = complaintRepo.findById(votingRequestDto.getComplaintId())
                .orElseThrow(() -> new NotFoundException(404, "complaint does not exists"));

        User voter = userRepo.findById(votingRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException(404, "user does not exists"));

        if (!voter.getFlat().getFlatCode().equals(complaint.getUser().getFlat().getFlatCode())) {
            throw new WrongInputException(400, "You can only vote on complaints filed by your flatmates");
        }
        Vote vote = new Vote();
        vote.setVoteType(votingRequestDto.getVoteType());
        vote.setComplaint(complaint);
        vote.setVoter(voter);
        voteRepo.save(vote);
        VotingResponseDto votingResponseDto = new VotingResponseDto();
        votingResponseDto.setVoteType(vote.getVoteType());
        votingResponseDto.setVotedBy(voter.getUsername());
        return ResponseEntity.ok(new CommonResponse("Voting recorded successfully", 200, votingResponseDto));
    }

    @Override
    public ResponseEntity<?> fetchMostVotedComplaint(Long flatId) {
        List<Complaint> complaintList = complaintRepo.findByFlatId(flatId);
        List<TrendingVoteResponseDto> trendingVoteResponseDtoList = new ArrayList<>();
        complaintList.stream().forEach(complaint -> {
            TrendingVoteResponseDto trendingVoteResponseDto = generateResponse(complaint);
            trendingVoteResponseDtoList.add(trendingVoteResponseDto);
        });
        List<TrendingVoteResponseDto> responseDtos = trendingVoteResponseDtoList.stream()
                .sorted(Comparator.comparingLong(TrendingVoteResponseDto::getNetVotes).reversed()).collect(Collectors.toList());
        return ResponseEntity.ok(new CommonResponse("Treanding complaint", 200, responseDtos));


    }

    private TrendingVoteResponseDto generateResponse(Complaint complaint) {
        long upVoteCount = complaint.getVotes().stream().filter(vote -> vote.getVoteType() == VoteType.UPVOTE).count();
        long downVoteCount = complaint.getVotes().stream().filter(vote -> vote.getVoteType() == VoteType.DOWNVOTE).count();
        TrendingVoteResponseDto trendingVoteResponseDto = new TrendingVoteResponseDto();
        trendingVoteResponseDto.setUpVotes(upVoteCount);
        trendingVoteResponseDto.setDownVotes(downVoteCount);
        trendingVoteResponseDto.setNetVotes(upVoteCount - downVoteCount);
        trendingVoteResponseDto.setTitle(complaint.getTitle());
        trendingVoteResponseDto.setResolved(complaint.isResolve());
        trendingVoteResponseDto.setComplaintType(complaint.getComplaintType());
        trendingVoteResponseDto.setDescription(complaint.getDescription());
        trendingVoteResponseDto.setId(complaint.getComplaintId());
        trendingVoteResponseDto.setSeverityLevel(complaint.getSeverityLevel());
        return trendingVoteResponseDto;
    }
}
