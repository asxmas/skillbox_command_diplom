package ru.skillbox.team13.test_util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PostDto;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Service
@RequiredArgsConstructor
public class RequestService {

    @Autowired
    private final MockMvc mockMvc;

    @Autowired
    private final ObjectMapper om;

    @SneakyThrows
    public MvcResult doRequest(RequestBuilder req, ResultMatcher status, boolean doPrint) {
        ResultActions ra = mockMvc.perform(req);
        if (doPrint) ra.andDo(print());
        return ra.andExpect(status).andReturn();
    }

    @SneakyThrows
    public String getAsString(RequestBuilder req, boolean doPrint) {
        MvcResult mr = doRequest(req, MockMvcResultMatchers.status().isOk(), doPrint);
        return mr.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public DTOWrapper getAsWrapper(RequestBuilder req, boolean doPrint) {
        String json = getAsString(req, doPrint);
        return om.readValue(json, DTOWrapper.class);
    }

    @SneakyThrows
    public String getDataAsString(RequestBuilder req, boolean doPrint) {
        DTOWrapper wr = getAsWrapper(req, doPrint);
        return om.writeValueAsString(wr.getData());
    }

    @SneakyThrows
    public List<PostDto> getAsPostsDtoList(RequestBuilder req, boolean doPrint) {
        String data = getDataAsString(req, doPrint);
        return Arrays.asList(om.readValue(data, PostDto[].class));
    }
    @SneakyThrows
    public PostDto getAsPostDto(RequestBuilder req, boolean doPrint) {
        String data = getDataAsString(req, doPrint);
        return om.readValue(data, PostDto.class);
    }

    @SneakyThrows
    public List<CommentDto> getAsCommentDtoList(RequestBuilder req, boolean doPrint) {
        String data = getDataAsString(req, doPrint);
        return Arrays.asList(om.readValue(data, CommentDto[].class));
    }

    @SneakyThrows
    public CommentDto getAsCommentDto(RequestBuilder req, boolean doPrint) {
        String data = getDataAsString(req, doPrint);
        return om.readValue(data, CommentDto.class);
    }
}
