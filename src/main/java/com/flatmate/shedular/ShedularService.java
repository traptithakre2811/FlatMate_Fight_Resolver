package com.flatmate.shedular;

import com.flatmate.entity.Flat;
import com.flatmate.entity.User;
import com.flatmate.exceptionhandler.customeexception.NotFoundException;
import com.flatmate.repository.FlatRepo;
import com.flatmate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShedularService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FlatRepo flatRepo;

    @Scheduled(cron = "0 */2 * * * ?")
    public void bestFlatmateBagde() {
        List<Flat> flats = flatRepo.findAll();
        for (Flat flat : flats) {
            Optional<User> bestFlatMateOptional = userRepo.findTopUserByFlatId(flat.getId());
            if (bestFlatMateOptional.isPresent()) {
                User bestFlatMate = bestFlatMateOptional.get();

                List<User> flatMates = userRepo.findByFlat(flat);
                for (User user : flatMates) {
                    user.setBadge(null);
                    userRepo.save(user);
                }
                bestFlatMate.setBadge("BestFlatMate");
                bestFlatMate.setKarmaPoints(0);
                userRepo.save(bestFlatMate);
                for (User user : flatMates) {
                    if(user.getId()!= bestFlatMate.getId()){
                    user.setKarmaPoints(0);
                    userRepo.save(user);
                }}
            }
        }

    }



}
