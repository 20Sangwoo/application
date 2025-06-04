package com.example.application;

import com.example.application.web.interceptor.ExecutionTimeInterceptor;
import com.example.application.web.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

  private final LoginCheckInterceptor loginCheckInterceptor;
  private final ExecutionTimeInterceptor executionTimeInterceptor;

  @Bean
  public ChatClient openAIChatClient(OpenAiChatModel chatModel) {
    return ChatClient.create(chatModel);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
// case2) 화이트리스트 전략
    registry.addInterceptor(loginCheckInterceptor)
        .order(2)
        .excludePathPatterns("/**")   //루트부터 하위경로 모두 인터셉터 대상으로 미포함.
        .addPathPatterns(
            "/member/**"           //
        );
//    registry.addInterceptor(executionTimeInterceptor)
//        .order(1);
  }
}
