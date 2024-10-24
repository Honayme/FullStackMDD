package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicResponseDTO;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing topics.
 */
@Service
public class TopicServiceImpl implements ITopicService {

    private static final Logger log = LogManager.getLogger(TopicServiceImpl.class);

    private final TopicRepository topicRepository;
    private final UserServiceImpl userService;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, UserServiceImpl userService) {
        this.topicRepository = topicRepository;
        this.userService = userService;
    }

    @Override
    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id)
                .or(() -> {
                    try {
                        throw new TopicNotFoundException("Topic not found");
                    } catch (TopicNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public Optional<Topic> getTopicByTitle(String name) {
        return topicRepository.findTopicByTitle(name);
    }

    @Override
    public List<TopicResponseDTO> getAllTopics() {
        List<Long> myTopicsId = getUserSubscribedTopicsPart().stream().map(Topic::getId).toList();
        return topicRepository.findAll().stream()
                .map(topic -> {
                    TopicResponseDTO dto = new TopicResponseDTO(topic);
                    dto.setIsSubscribed(myTopicsId.contains(dto.getId()));
                    return dto;
                })
                .toList();
    }

    @Override
    public List<Topic> getUserSubscribedTopicsPart() {
        User user = getAuthenticatedUser();
        return topicRepository.findAllTopicsByUsersId(user.getId());
    }

    @Override
    public List<TopicResponseDTO> getUserSubscribedTopics() {
        List<Long> myTopicsId = getUserSubscribedTopicsPart().stream().map(Topic::getId).toList();
        return topicRepository.findAllTopicsByUsersId(getAuthenticatedUser().getId()).stream()
                .map(topic -> {
                    TopicResponseDTO dto = new TopicResponseDTO(topic);
                    dto.setIsSubscribed(myTopicsId.contains(dto.getId()));
                    return dto;
                })
                .toList();
    }

    @Override
    public Topic createTopic(TopicResponseDTO topicResponseDTO) throws TopicNotFoundException {
        if (getTopicByTitle(topicResponseDTO.getTitle()).isPresent()) {
            throw new TopicNotFoundException("Topic already exists");
        }

        Topic topic = Topic.builder()
                .title(topicResponseDTO.getTitle())
                .description(topicResponseDTO.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
        return topicRepository.save(topic);
    }

    @Override
    public String saveSubScribe(long topicId, Boolean isSubscribed) {
        User user = getAuthenticatedUser();
        Topic topic = topicRepository.findById(topicId).orElseThrow();

        if (isSubscribed) {
            topic.getUsers().add(user);
            topicRepository.save(topic);
            log.info("Subscribed to topic successfully");
            return "Subscribed to topic successfully";
        } else {
            unsubscribe(topicId);
            log.info("Unsubscribed from topic successfully");
            return "Unsubscribed from topic successfully";
        }
    }

    @Override
    public boolean isUserSubscribed(Topic topic) {
        return topic.getUsers().contains(getAuthenticatedUser());
    }

    @Override
    public boolean isUserSubscribedToTopicById(long topicId) {
        return isUserSubscribed(topicRepository.findById(topicId).orElseThrow());
    }

    @Override
    public void unsubscribe(long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        User user = getAuthenticatedUser();
        if (topic.getUsers().contains(user)) {
            topic.getUsers().remove(user);
            topicRepository.save(topic);
        } else {
            log.info("User is not subscribed to the topic");
        }
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.getUserByEmail(userDetails.getUsername());
    }
}
