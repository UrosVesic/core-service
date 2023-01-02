package rs.urosvesic.coreservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.dto.ReactionDto;
import rs.urosvesic.coreservice.model.Reaction;
import rs.urosvesic.coreservice.repository.PostRepository;
import rs.urosvesic.coreservice.util.UserUtil;

/**
 * @author UrosVesic
 */
@RequiredArgsConstructor
@Component
public class ReactionMapper implements GenericMapper<ReactionDto, Reaction> {

    private final PostRepository postRepository;
    private final UserUtil userUtil;


    @Override
    public Reaction toEntity(ReactionDto dto) {
        Reaction reaction = new Reaction();
        reaction.setPost(postRepository.findById(dto.getPostId()).orElseThrow(()->new RuntimeException("Post not found")));
        reaction.setReactionType(dto.getReactionType());
        reaction.setCreatedBy(userUtil.getCurrentUser());
        return reaction;
    }

    @Override
    public ReactionDto toDto(Reaction entity) {
        return null;
    }
}
