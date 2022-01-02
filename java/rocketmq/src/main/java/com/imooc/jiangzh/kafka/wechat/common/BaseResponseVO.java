package com.imooc.jiangzh.kafka.wechat.common;

import java.util.UUID;
import lombok.Data;

/**
 * @author : jiangzh
 * @description : 公共返回对象
 **/
@Data
public class BaseResponseVO<M> {

  private String requestId;
  private M result;

  public static<M> BaseResponseVO success(){
    BaseResponseVO baseResponseVO = new BaseResponseVO();
    baseResponseVO.setRequestId(genRequestId());

    return baseResponseVO;
  }

  public static<M> BaseResponseVO success(M result){
    BaseResponseVO baseResponseVO = new BaseResponseVO();
    baseResponseVO.setRequestId(genRequestId());
    baseResponseVO.setResult(result);

    return baseResponseVO;
  }

  private static String genRequestId(){
    return UUID.randomUUID().toString();
  }

}
