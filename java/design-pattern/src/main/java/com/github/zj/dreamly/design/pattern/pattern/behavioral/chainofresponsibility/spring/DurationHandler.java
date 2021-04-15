package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.spring;

import org.springframework.stereotype.Component;

@Component
public class DurationHandler implements Handler {

  @Override
  public void filterTask(HandlerContext ctx, Task task) {
    System.out.println("时效性检验");
    ctx.fireTaskFiltered(task);
  }
}
