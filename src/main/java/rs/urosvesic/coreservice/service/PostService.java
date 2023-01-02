package rs.urosvesic.coreservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.urosvesic.coreservice.dto.PostRequest;
import rs.urosvesic.coreservice.dto.PostResponse;
import rs.urosvesic.coreservice.dto.UserDto;
import rs.urosvesic.coreservice.mapper.PostMapper;
import rs.urosvesic.coreservice.mapper.PostRequestMapper;
import rs.urosvesic.coreservice.model.Post;
import rs.urosvesic.coreservice.repository.PostRepository;
import rs.urosvesic.coreservice.repository.TopicRepository;
import rs.urosvesic.coreservice.util.UserUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postResponseMapper;
    private final PostRequestMapper postRequestMapper;
    private final TopicRepository topicRepository;
    private final UserService userService;
//    private PostReportRepository postReportRepository;
//    private ReportedPostMapper reportedPostMapper;
    private ApplicationContext context;
    @Transactional
    public List<PostResponse> getAllPosts(){
        return postRepository.findAll()
                .stream()
                .map(postResponseMapper::toDto)
                .sorted(Comparator.comparingInt(p -> p.getLikes() - p.getDislikes()))
                .collect(Collectors.toList());

    }

    @Transactional
    public PostResponse createPost(PostRequest postRequest) {
        Post post = postRequestMapper.toEntity(postRequest);
        postRepository.save(post);
        return postResponseMapper.toDto(post);
    }
    @Transactional
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new RuntimeException("Post not found"));
        return postResponseMapper.toDto(post);
    }

    @Transactional
    public List<PostResponse> getAllPostsForUser(String username) {
        List<Post> posts = postRepository.findAllByCreatedBy_usernameAndDeletebByAdminIsNull(username);
        return posts.stream().map(postResponseMapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if(!post.getCreatedBy().getUsername().equals(UserUtil.getCurrentUsername())){
            throw new RuntimeException("You are not owner of the post");
        }
        post.returnChildRepositories(context).forEach(r->r.deleteByParent(post));
        postRepository.deleteById(id);
    }

    @Transactional
    public List<PostResponse> getAllPostsForFollowingUsers() {
        List<UserDto> followingForUser = userService.getAllFollowingForUser(UserUtil.getCurrentUsername());
        List<Post> posts = postRepository.findByCreatedBy_userIdInAndDeletebByAdminIsNull(followingForUser.stream().map(UserDto::getUsername).collect(Collectors.toList()));
        posts = posts.stream().sorted(Comparator.comparing(Post::getCreatedDate).reversed()).collect(Collectors.toList());
        return posts.stream().map(postResponseMapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public void updatePost(Long id,PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(()->new RuntimeException("Post not found"));
        post.setTopic(topicRepository.getByName(postRequest.getTopicName()).orElseThrow(()->new RuntimeException("Topic not found")));
        post.setContent(postRequest.getContent());
        post.setTitle(postRequest.getTitle());
        postRepository.save(post);
    }
//    @Transactional
//    public Set<ReportedPostDto> getAllUnsolvedReportedPosts() {
//        List<PostReport> postReports = postReportRepository.findAllByReportStatus(ReportStatus.UNSOLVED);
//        Set<Post> reportedPosts;
//        reportedPosts = postReports.stream().map(PostReport::getPost).collect(Collectors.toSet());
//        Set<ReportedPostDto> collect = reportedPosts.stream().map(post -> reportedPostMapper.toDto(post)).collect(Collectors.toSet());
//        return collect.stream().sorted(Comparator.comparing(ReportedPostDto::getReportCount)).collect(Collectors.toCollection(LinkedHashSet::new));
//    }
//    @Transactional
//    public Set<ReportedPostDto> getAllSolvedReportedPosts() {
//        List<ReportStatus> statuses = new ArrayList<>();
//        statuses.add(ReportStatus.APPROVED);
//        statuses.add(ReportStatus.DELETED);
//        List<PostReport> postReports = postReportRepository.findAllByReportStatusIn(statuses);
//        Set<Post> reportedPosts;
//        reportedPosts = postReports.stream().map(PostReport::getPost).collect(Collectors.toSet());
//        return reportedPosts.stream().map(post -> reportedPostMapper.toDto(post)).collect(Collectors.toSet());
//    }
//    @Transactional
//    public void softDeletePost(Long id) {
//        Optional<Post> optPost = postRepository.findById(id);
//        Post post = optPost.orElseThrow(()->new MyRuntimeException("Post not found"));
//        post.setDeletebByAdmin(Instant.now());
//        postRepository.save(post);
//        List<PostReport> postReports = postReportRepository.findByPost_id(id);
//        postReports.forEach(report->report.setReportStatus(ReportStatus.DELETED));
//        //postReports = postReports.stream().peek(report->report.setReportStatus(ReportStatus.DELETED)).collect(Collectors.toList());
//        postReportRepository.saveAll(postReports);
//    }

    public List<PostResponse> getAllPostsForTopic(String topicName) {
        List<Post> posts = postRepository.findByTopic_name(topicName);
        return posts.stream()
                .map(postResponseMapper::toDto)
                .sorted((p1,p2)->likeDislikeDifference(p2)-likeDislikeDifference(p1))
                .collect(Collectors.toList());
    }

    public int likeDislikeDifference(PostResponse p){
        return p.getLikes()-p.getDislikes();
    }
}
