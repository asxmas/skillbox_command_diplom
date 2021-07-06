package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.SuccessDto;
import ru.skillbox.team13.dto.TagDto;
import ru.skillbox.team13.exception.UnauthorizedException;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.service.impl.TagServiceImpl;

@RestController
@RequestMapping("/api/v1/tags/")
@RequiredArgsConstructor
public class TagController {

    private final TagServiceImpl tagServiceImpl;

    @GetMapping
    public ResponseEntity<DTOWrapper> findTag(@RequestParam String tag,
                                              @RequestParam(required = false, defaultValue = "0") int offset,
                                              @RequestParam(required = false, defaultValue = "20") int itemPerPage){
        try {
            return new ResponseEntity<>(tagServiceImpl.getTags(tag, offset, itemPerPage), HttpStatus.OK);
        }catch (UnauthorizedException a){
            throw new UnauthorizedException("invalid_request");
        }

    }
    @PostMapping
    public ResponseEntity<DTOWrapper> addTag(@RequestBody TagDto tagDto){
        try {
            return ResponseEntity.ok(WrapperMapper.wrap(tagServiceImpl.addTag(tagDto), true));
        } catch (UnauthorizedException a){
            throw new UnauthorizedException("invalid_request");
        }
    }

    @DeleteMapping
    public ResponseEntity<DTOWrapper> deleteTag(@RequestParam Integer id){
        try {
            return ResponseEntity.ok(WrapperMapper.wrap(tagServiceImpl.deleteTag(id), true));
        } catch (UnauthorizedException a) {
            throw new UnauthorizedException("tag not found");
        }
    }
}
