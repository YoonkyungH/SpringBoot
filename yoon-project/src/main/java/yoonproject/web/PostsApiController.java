package yoonproject.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yoonproject.service.PostsService;
import yoonproject.web.dto.PostsResponseDto;
import yoonproject.web.dto.PostsSaveRequestDto;
import yoonproject.web.dto.PostsUpdateRequestDto;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
}
