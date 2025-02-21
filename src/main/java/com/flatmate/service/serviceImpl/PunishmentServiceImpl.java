package com.flatmate.service.serviceImpl;

import com.flatmate.dto.PunishmentResponse;
import com.flatmate.entity.Complaint;
import com.flatmate.entity.User;
import com.flatmate.entity.Vote;
import com.flatmate.enums.PunishmentType;
import com.flatmate.enums.VoteType;
import com.flatmate.repository.ComplaintRepo;
import com.flatmate.repository.UserRepo;
import com.flatmate.response.CommonResponse;
import com.flatmate.service.PunishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class PunishmentServiceImpl implements PunishmentService {

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ResponseEntity<?> punishemt(Long flatId) {
        System.err.println(flatId);
        List<Complaint> complaintsofFlat = complaintRepo.findByFlatId(flatId);
        System.out.println(complaintsofFlat +" conkkd");
        List<Complaint> upVoteComplaints = complaintsofFlat.stream().filter(complaint -> complaint.isResolve() == false).collect(Collectors.toList());
        System.out.println(upVoteComplaints);
        PunishmentType[] punishmentTypes = PunishmentType.values();
        Random random = new Random();

        List<PunishmentResponse> punishmentResponseList = new ArrayList<>();
        for (Complaint complaint : upVoteComplaints) {
            PunishmentResponse punishmentResponse = new PunishmentResponse();
            User user = complaint.getUser();
            long count = complaint.getVotes().stream().filter(vote -> vote.getVoteType() == VoteType.UPVOTE).count();
            System.out.println(complaint.getVotes() + "complaint vote");
            System.out.println(count);
            if (count > 2) {
                Integer i = random.nextInt(punishmentTypes.length);
                complaint.setPunishment(punishmentTypes[i]);
                complaintRepo.save(complaint);
                punishmentResponse.setPunishmentType(punishmentTypes[i]);
                punishmentResponse.setUserName(user.getUsername());
                punishmentResponse.setFlatCode(user.getFlat().getFlatCode());
                punishmentResponseList.add(punishmentResponse);
            }
        }

        return ResponseEntity.ok(new CommonResponse("punishment list is ready", 200, punishmentResponseList));
    }

    public static void main(String[] args) {
        PunishmentType[] punishmentTypes = PunishmentType.values();
        Random random = new Random();
        Integer i = random.nextInt(punishmentTypes.length);
        System.out.println(punishmentTypes[i]);
    }
}
