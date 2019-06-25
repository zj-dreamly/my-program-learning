package com.github.zj.dreamly.design.pattern.principle.singleresponsibility;

/**
 * @author 苍海之南
 */
public class Method {
    private void updateUserInfo(String userName,String address){
        userName = "geely";
        address = "beijing";
    }

    private void updateUserInfo(String userName,String... properties){
        userName = "geely";
//        address = "beijing";
    }

    private void updateUsername(String userName){
        userName = "geely";
    }
    private void updateUserAddress(String address){
        address = "beijing";
    }

    private void updateUserInfo(String userName,String address,boolean bool){
        if(bool){
            //todo something1
        }else{
            //todo something2
        }
        userName = "geely";
        address = "beijing";
    }


}
