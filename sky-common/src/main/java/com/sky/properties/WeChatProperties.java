package com.sky.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.wechat")
@Data
public class WeChatProperties {

    private String appid; //アプレットのappid
    private String secret; //アプレットのシークレットキー
    private String mchid; //店の番号
    private String mchSerialNo; //加盟店API証明書の証明書のシリアル番号
    private String privateKeyFilePath; //店のシークレットキー
    private String apiV3Key; //証明書を復号化するためのキー
    private String weChatPayCertFilePath; //weChatPay証明書
    private String notifyUrl; //支払いが成功したコールバックアドレス
    private String refundNotifyUrl; //払い戻しが成功したコールバックアドレス

}
