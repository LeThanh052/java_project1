package vn.ledeem.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.ledeem.jobhunter.domain.Skill;
import vn.ledeem.jobhunter.domain.Subscriber;
import vn.ledeem.jobhunter.domain.response.subscriber.ResCreateSubscriberDTO;
import vn.ledeem.jobhunter.domain.response.subscriber.ResUpdateSubscriberDTO;
import vn.ledeem.jobhunter.repository.SkillRepository;
import vn.ledeem.jobhunter.repository.SubscriberRepository;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final SkillRepository skillRepository;

    public SubscriberService(SubscriberRepository subscriberRepository, SkillRepository skillRepository) {
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
    }

    public Optional<Subscriber> fetchSubscriberById(Long id) {
        return this.subscriberRepository.findById(id);
    }

    public boolean isEmailExist(String email) {
        return this.subscriberRepository.existsByEmail(email);
    }

    public ResCreateSubscriberDTO create(Subscriber subscriber) {
        // check skills
        if (subscriber.getSkills() != null) {
            List<Long> reqSkills = subscriber.getSkills()
                    .stream()
                    .map(x -> x.getId())
                    .collect(Collectors.toList());

            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            subscriber.setSkills(dbSkills);
        }

        // create subscriber
        Subscriber currentSubscriber = this.subscriberRepository.save(subscriber);

        // convert response
        ResCreateSubscriberDTO dto = new ResCreateSubscriberDTO();
        dto.setId(currentSubscriber.getId());
        dto.setEmail(currentSubscriber.getEmail());
        dto.setName(currentSubscriber.getName());
        dto.setCreatedAt(currentSubscriber.getCreatedAt());
        dto.setCreatedBy(currentSubscriber.getCreatedBy());

        if (currentSubscriber.getSkills() != null) {
            List<String> skills = currentSubscriber.getSkills()
                    .stream()
                    .map(item -> item.getName())
                    .collect(Collectors.toList());
            dto.setSkills(skills);
        }

        return dto;
    }

    public ResUpdateSubscriberDTO update(Subscriber subscriber, Subscriber subscriberInDB) {
        // check skills
        if (subscriber.getSkills() != null) {
            List<Long> reqSkills = subscriber.getSkills()
                    .stream()
                    .map(x -> x.getId())
                    .collect(Collectors.toList());

            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            subscriberInDB.setSkills(dbSkills);
        }

        // update subscriber
        Subscriber currentSubscriber = this.subscriberRepository.save(subscriberInDB);

        // convert response
        ResUpdateSubscriberDTO dto = new ResUpdateSubscriberDTO();
        dto.setId(currentSubscriber.getId());
        dto.setEmail(currentSubscriber.getEmail());
        dto.setName(currentSubscriber.getName());
        dto.setCreatedAt(currentSubscriber.getCreatedAt());
        dto.setUpdatedAt(currentSubscriber.getUpdatedAt());
        dto.setUpdatedBy(currentSubscriber.getUpdatedBy());

        if (currentSubscriber.getSkills() != null) {
            List<String> skills = currentSubscriber.getSkills()
                    .stream()
                    .map(item -> item.getName())
                    .collect(Collectors.toList());
            dto.setSkills(skills);
        }

        return dto;
    }
}
