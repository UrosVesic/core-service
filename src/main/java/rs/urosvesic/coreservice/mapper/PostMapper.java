package rs.urosvesic.coreservice.mapper;

import lombok.AllArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;
import rs.urosvesic.coreservice.dto.PostResponse;
import rs.urosvesic.coreservice.model.Post;
import rs.urosvesic.coreservice.model.Reaction;
import rs.urosvesic.coreservice.model.ReactionType;
import rs.urosvesic.coreservice.repository.CommentRepository;
import rs.urosvesic.coreservice.repository.ReactionRepository;
import rs.urosvesic.coreservice.util.UserUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
@Component
public class PostMapper implements GenericMapper<PostResponse, Post> {

    private CommentRepository commentRepository;
    private ReactionRepository reactionRepository;


    @Override
    public Post toEntity(PostResponse dto) {
        return null;
    }

    @Override
    public PostResponse toDto(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setTopicName(post.getTopic().getName());
        postResponse.setUsername(post.getCreatedBy().getUsername());
        postResponse.setCommentCount(getCommentCount(post));
        postResponse.setDislikes(getDislikesCount(post));
        postResponse.setLikes(getLikesCount(post));
        postResponse.setLiked(isLiked(post.getId()));
        postResponse.setDisliked(isDisliked(post.getId()));
        postResponse.setUsernameLikes(getUsernameLikes(post));
        postResponse.setUsernameDislikes(getUsernameDislikes(post));
        PrettyTime p = new PrettyTime();
        postResponse.setDuration( p.format(post.getCreatedDate()));
        return postResponse;
    }

    private List<String> getUsernameDislikes(Post post) {
        List<Reaction> likes = reactionRepository.findByPostAndReactionType(post, ReactionType.DISLIKE);
        return likes.stream().map((like)->like.getCreatedBy().getUsername()).collect(Collectors.toList());
    }

    private List<String> getUsernameLikes(Post post) {
        List<Reaction> likes = reactionRepository.findByPostAndReactionType(post, ReactionType.LIKE);
        return likes.stream().map((like)->like.getCreatedBy().getUsername()).collect(Collectors.toList());
    }

    private boolean isDisliked(Long id) {
        Optional<Reaction> optReaction = reactionRepository.findByPost_idAndCreatedBy_Username(id, UserUtil.getCurrentUsername());
        return optReaction.filter(reaction -> reaction.getReactionType() == ReactionType.DISLIKE).isPresent();
    }

    private boolean isLiked(Long postId) {
        Optional<Reaction> optReaction = reactionRepository.findByPost_idAndCreatedBy_Username(postId,UserUtil.getCurrentUsername());
        return optReaction.filter(reaction -> reaction.getReactionType() == ReactionType.LIKE).isPresent();
    }

    private Integer getDislikesCount(Post post) {
        return reactionRepository.findByPostAndReactionType(post, ReactionType.DISLIKE).size();
    }

    private Integer getLikesCount(Post post) {
        return reactionRepository.findByPostAndReactionType(post, ReactionType.LIKE).size();
    }

    private Integer getCommentCount(Post post) {
       return commentRepository.findAllByPost(post).size();
    }
}
