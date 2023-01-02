package rs.urosvesic.coreservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.urosvesic.coreservice.dto.ReactionDto;
import rs.urosvesic.coreservice.mapper.PostToNotificationMapper;
import rs.urosvesic.coreservice.mapper.ReactionMapper;
import rs.urosvesic.coreservice.model.Reaction;
import rs.urosvesic.coreservice.repository.ReactionRepository;
import rs.urosvesic.coreservice.util.UserUtil;

import java.util.Optional;

/**
 * @author UrosVesic
 */
@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;
    private final PostToNotificationMapper postToNotificationMapper;
    private final NotificationService notificationService;

    @Transactional
    public void react(ReactionDto reactionDto){
        Optional<Reaction> reactOpt=reactionRepository.findByPost_idAndCreatedBy_Username(reactionDto.getPostId(), UserUtil.getCurrentUsername());
        Reaction reactionEntity = reactionMapper.toEntity(reactionDto);
        if(reactOpt.isEmpty()){
            reactionRepository.save(reactionEntity);
            notificationService.sendNotification(reactionEntity.getPost(), reactionDto.getReactionType());
            return;
        }
        Reaction reaction = reactOpt.get();
        reactionRepository.delete(reaction);

        if(reaction.getReactionType()!=reactionDto.getReactionType()){
            reactionRepository.save(reactionEntity);
            notificationService.sendNotification(reactionEntity.getPost(),reactionDto.getReactionType());
        }
    }
}
