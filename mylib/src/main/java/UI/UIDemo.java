package UI;

import TimeRecord.*;
import TimeRecord.TimeRecordAPI.*;


/**
 *功能库的使用DEMO，UI可参考本DEMO进行开发
 * @author Hugh
 * @Time 2016/12/12
 */

public class UIDemo implements MsgCallBack{

    TimeRecordAPI API = null;
    public  static void main(String[] args)
    {
        UIDemo UI = new UIDemo();
        UI.StartBusiness();
        UI.EndBusiness();
    }




    /**TRCallBack Implement from TimeRecordAPI interface MsgCallBack*/
    public int TRCallBack (TimeRecordAPI.enMsgType Msg, Object struct, int nValue, String sStr)
    {
        switch (Msg)
        {
            case MSG_SysStatus:
            {
                if( struct instanceof SysStatus_S)
                {
                    SysStatus_S Sta =  (SysStatus_S)struct;
                    System.out.println("GPRS_IP="+Sta.sGPRS_IP);
                    System.out.println("nGPSAvailable="+Sta.nGPRSStatus);
                    //...
                }
                else
                {
                    System.out.println("enMsgType dos'nt match struct");
                }
                break;
            }
            case MSG_CustomizedMsg:
            {
                if( struct instanceof CustomizedMsg)
                {
                    CustomizedMsg CusMsg =  (CustomizedMsg)struct;
                    System.out.println("nMsgCategory="+CusMsg.nMsgCategory);
                    System.out.println("MsgText="+CusMsg.sMsg);
                }
                else
                {
                    System.out.println("enMsgType don't match any struct");
                }
                break;
            }
            //Other Msg......
            default:
                break;
        }
        return  0;
    }


    public int StartBusiness()
    {
        //生成对象与初始化
        API = new TimeRecordAPI();
        API.TR_Init(this);

        //设置网络参数
        NetServerPara_S NetServer = API.new NetServerPara_S();
        NetServer.lMainServerPort = 9000;
        NetServer.sMainServerDomain = new String("192.168.1.100");;
        API.TR_SetNetServerPara(NetServer);

        //获取网络参数
        NetServerPara_S Get = API.TR_GetNetServerPara();
        System.out.println("Get NetPara lMainServerPort = "+ Get.lMainServerPort);
        System.out.println("sMainServerDomain = " + Get.sMainServerDomain);


        //注意：系统状态有两种返回方式
        //1、调用TR_GetSysStatus主动获取，成功获取后返回对象引用。
        //2、必要时功能库会主动发送系统状态信息，这时会通过回调接口返回，消息类型为MSG_SysStatus
        SysStatus_S Status =   API.TR_GetSysStatus();
        System.out.println("GPRS_IP="+Status.sGPRS_IP);
        System.out.println("nGPSAvailable="+Status.nGPRSStatus);


        return 0;
    }

    public int EndBusiness()
    {
        //释放功能库
        API.TR_CleanUp();
        return 0;
    }
}
