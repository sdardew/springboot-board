package spring.service.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.domain.posts.PostsRepository;
import spring.web.dto.PostsSaveRequestDto;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
