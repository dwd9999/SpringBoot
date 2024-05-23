package com.ssafy;

import com.ssafy.board.model.mapper.BoardMapper;
import com.ssafy.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ShutdownEventListener implements ApplicationListener<ContextClosedEvent> {

    private final UserMapper userMapper;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        userMapper.destroyRefreshToken("gadin8631");
        userMapper.destroyRefreshToken("juwon");
        userMapper.deleteUser("gadin8631");
        userMapper.deleteUser("juwon");
    }
}
