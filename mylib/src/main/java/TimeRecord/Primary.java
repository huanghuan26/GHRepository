package TimeRecord;

/**
 *功能库主类，库内其他类对象与业务均由本类生成与启动。
 * @author Hugh
 * @Time 2016/12/12
 */

import TimeRecord.TimeRecordAPI.*;
import static TimeRecord.TimeRecordAPI.enMsgType.*;


public class Primary {
    static final String BCD = "byte";
    TimeRecordAPI m_API;
    SysInfo_S m_SysInfo;
    NetServerPara_S m_NetServerPara;
    SysStatus_S m_Status;
    FaceRecognizeAccount_S m_Face;

    /**Protocal Para*/
    public long  m_lGPSReportWay;                   //位置汇报策略，0：定时汇报； 1：定距汇报； 2：定时和定距汇报
    public long  m_lGPSPositionWay;                 //位置汇报方案，0：根据ACC状态；1：根据登录状态和ACC状态，先判断登录状态，若登录再根据ACC状态
    public long  m_lTimeIntervalDriverUnlogin;      //驾驶员未登录汇报时间间隔，单位为秒(s),>0
    public long  m_lTimeIntervalDormancy;           //休眠时汇报时间间隔，单位为秒(s),>0
    public long  m_lTimeIntervalAlarm;              //紧急报警时汇报时间间隔，单位为秒(s),>0
    public long  m_lTimeIntervalDefault;            //缺省时间汇报间隔，单位为秒(s),>0
    public long  m_lRangeIntervalDefault;           //缺省时间汇报间隔，单位为米(m),>0
    public long  m_lRangeIntervalDriverUnlogin;     //驾驶员未登录汇报距离间隔，单位为米(m),>0
    public long  m_lRangeIntervalDormancy;          //休眠时汇报距离间隔，单位为米(m),>0
    public long  m_lRangeIntervalAlarm;             //紧急报警时汇报距离间隔，单位为米(m),
    public long  m_lSwerveAngle;                    //拐点补传角度， <180°

    public String m_sPhoneNoOfPlatform;             //监控平台电话号码
    public String m_sPhoneNoOfResetTerminal;        //复位电话号码，可采用此电话号码拨打终端电话让终端复位
    public String m_sPhoneNoOfFactoryReset;         //恢复出厂设置电话号码，拨打此电话号码终端恢复出厂设置
    public String m_sPhoneNoOfPlatformSMS;          //监控平台 SMS 电话号码
    public String m_sPhoneNoForTerminalAlarm;       //接收终端 SMS 文本报警号码
    public long m_lWayPickUp;                       //终端电话接听策略，0:自动接听1:ACC ON 时自动接听，OFF 时手动接听
    public long m_lMaximunPhoneCallTime;            //每次最长通话时间，单位为秒(s),0 为不允许通话， 0xFFFFFFFF 为不限制
    public long m_lMaximunPhoneCallTimePerMonth;    //当月最长通话时间，单位为秒(s),0 为不允许通话， 0xFFFFFFFF 为不限制
    public String m_sPhoneNoOfListen;               //!!@@监听电话号码
    public String m_sSMSNoOfPlatformPrivilege;      //!!@@监管平台特权短信号码
    public long m_lAlarmMask;                       //报警屏蔽字。与位置信息报警标识相对应，相应位为1则相应报警被屏蔽
    public long m_lAlarmSwitchOfSMS;                //报警发送文本 SMS 开关，与位置信息报警标识相对应，相应位为 1 则相应报警时发送文本 SMS
    public long m_lAlarmTakePicture;                //报警拍摄开关，与位置信息报警标识相对应，相应位为1 则相应报警时摄像头拍摄
    public long m_lAlarmSavePhoto;                  //报警拍摄存储标识，与位置信息报警标识相对应，相应位为 1 则进行存储，否则实时长传
    public long m_lAlarmMark;                       //关键标识，与位置信息报警标识相对应，相应位为 1 则对相应报警为关键报警
    public long m_lMaximunSpeed;                    //最高速度，单位为公里每小时(km/h)
    public long m_lConsecutiveTimeOfOverSpeed;      //超速持续时间，单位为秒(s)
    public long m_lConsecutiveTimeOfDriving;        //连续驾驶时间门限，单位为秒(s)
    public long m_lConsecutiveTimeOfDrivePerday;    //当天累计驾驶时间门限，单位为秒(s)
    public long m_lMinimunTimeOfRest;               //最小休息时间，单位为秒(s)
    public long m_lMaximunTimeOfPark;               //最长停车时间，单位为秒(s)
    public long m_lPictureQuality;                  //图像/视频质量， 1-10,1 最好
    public long m_lPictureBriteness;                //亮度， 0-255
    public long m_lPictureContrast;                 //对比度， 0-127
    public long m_lPicSaturability;                 //饱和度， 0-127
    public long m_lPicColor;                        //色度， 0-255
    public long m_lVehicleMileage;                  //车辆里程表读数， 1/10km
    public long m_lVehicleProvinceID;               //车辆所在的省域 ID
    public long m_lVehicleCityID;                   //车辆所在的市域 ID
    public long m_lVehicleLicenceID;                //公安交通管理部门颁发的机动车号牌 !!@@
    public long m_lVehiclePlatColor;                //车牌颜色，按照 JT/T415-2006 的 5.4.12


    /**GPS Para*/
    public char m_cGPSAvailable;		        //GPS状态；  A:有效  V:无效
    public char m_cLatitudeNS;			        //南北纬度   N:北纬；S：南纬
    public char m_cLongitudeEW;		            //东西经度； E:东经；W：西经
    public double m_dLatitude;			        //纬度值 dd.dddddd
    public double m_dLongitude;		            //经度值 ddd.dddddd
    public double m_dSpeed;			            //速度;  xxx.xx(Km/h)
    public int m_nDirection;			        //GPS方向

    /**Coach Para*/
    public byte[] m_byCoachID = new byte[16];          //教练员编号  统一编号
    public byte[] m_byCoachIdentity = new byte[18];    //教练员身份证号ASCII 码，不足 18 位前补 0x00
    public byte[] m_byCoachLevel = new byte[2];    //准教车型A1\A2\A3\B1\B2\C1\C2\C3\C4\D\E\F

    /**student Para*/
    public byte[] m_byStudentID = new byte[16];        //学员编号  统一编号
    public byte[] m_byStudentsCoach = new byte[16];    //当前教练编号  统一编号
    public byte[] m_byTrainID = new byte[10];          //培训课程 课程编码见 平台技术规范 A4.2
    public long m_lLessonID;                           // 课堂 ID 标识学员的一次培训过程， 计时终端自行使用

    public long m_lTrainTimeTotality;                //总培训学时 单位： min
    public long m_lTrainTimeCurrentPart;             //当前培训部分已完成学时 单位： min
    public long m_lTrainMileTotality;                //总培训里程 单位： 1/10km
    public long m_lTrainMileCurrentPart;             //当前培训部分已完成里程 单位： 1/10km


    /**System Practical Para*/
    class PracticalPara{
        public  short TakePicInterval;          //定时拍照时间间隔 单位： min，默认值:15。在学员登录后间隔固定时间拍摄照片
        public  short UploadingPicWay;          //照片上传设置 0：不自动请求上传；1：自动请求上传
        public  short TTSPlay;                  //是否报读附加消息  1：自动报读； 2：不报读 控制是否报读消息中的附加消息
        public  short DeferTimeWhileShutdown;   //熄火后停止学时计时的延时时间  单位： min
        public long UploadingGPSInterval;       //熄火后 GNSS 数据包上传间隔  单位： s，默认值 3600， 0 表示不上传
        public long DeferTimeForCoachLogOut;    //熄火后教练自动登出的延时时间  单位： min，默认值 150
        public long ReVerifyTime;               //重新验证身份时间  单位： min，默认值 30
        public short OtherSchoolPermitOfCoach;  //教练跨校教学  1：允许 2：禁止
        public short OtherSchoolPermitOfstudent;//学员跨校学习 1：允许 2：禁止
    }


    public Primary(TimeRecordAPI api)
    {
        this.m_API = api;
        m_SysInfo = m_API.new SysInfo_S();
        m_NetServerPara = m_API.new NetServerPara_S();
        m_Status = m_API.new SysStatus_S();
        m_Status.nGPRSStatus = 1;
        m_Status.nGPSAvailable = 1;
        m_Status.nSignalIntensity = 1;
        m_Status.sGPRS_IP = "88.88.88.88";
        m_Status.nSateliteNum  = 6;
        m_Status.nPlatformConnection = 1;
        SetSystemInfo();

        m_Face = m_API.new FaceRecognizeAccount_S();
        m_Face.sApiKey = "I don't have it yet";
        m_Face.sApiSecret = "8888";

    }
    public  static void main(String[] args)
    {

    }


    public int StartBusiness()
    {
        System.out.println("Primary.StartBusiness Run");
        //System.out.print(System.getProperty("file.encoding"));
        CustomizedMsg CusMsg = m_API.new CustomizedMsg();
        CusMsg.nMsgCategory = 1;
        CusMsg.sMsg = "Customized Out put Message succeed!";
        m_API.CB.TRCallBack(MSG_CustomizedMsg,CusMsg, 0,null);


        return 0;
    }

    public int EndBusiness()
    {
        System.out.println("Primary EndBusiness");
       // System.out.println("sMainServerDomain = "+m_NetServerPara.sMainServerDomain);
        return 0;
    }


    /** UI_TerminalRegister 终端注册
     * @param sVehicleLicenceID 公安交通管理部门颁发的机动车号牌  !!@@
     * @param lPlatColor    车牌颜色 0：未上牌1：蓝色 2：黄色 3：黑色 4：百色9：其他
     * @return  0：成功向平台发送注册信息（不代表注册成功），-1：注册出错，-2：传入参数有误， -4：及以下：其他错误*/
    public int UI_TerminalRegister(String sVehicleLicenceID, long lPlatColor)
    {
        int rt = 0;//TR_TerminalRegister(VsehicleLicenceID, lPlatColor);
        return rt;
    }

    /** UI_TerminalLogout 终端注销 无需平台应答
     * @return  0：注销成功（无需平台应答），-1：注销出错， -4：及以下：其他错误*/
    public int UI_TerminalLogout()
    {
        return 0;
    }

    /** UI_CoachLogin 教练员登录
     * @param sCoachID 教练员编号  统一编号
     * @param byCoachIdentity 教练员身份证号ASCII 码，不足 18 位前补 0x00
     * @param byCoachLevel 准教车型A1\A2\A3\B1\B2\C1\C2\C3\C4\D\E\F
     * @return  0：本机正常登录（不代表登录平台成功），-1：登录出错，-2：传入参数有误*/
    public int UI_CoachLogin(String sCoachID, byte[] byCoachIdentity, byte[] byCoachLevel)
    {
         if(false)//TODO 其他非法输入检测
        {
            return -2;
        }
        return 0;
    }


    /** UI_CoachLogout 教练员登出
     * @param sCoachID 教练员编号  统一编号
     * @return  0：本机正常登出（不代表登出平台成功），-1：登出失败，-2：传入参数有误*/
    public int UI_CoachLogout(String sCoachID)
    {
        if(false)//TODO 其他非法输入检测
        {
            return -2;
        }
        return 0;
    }

    /** UI_StudentLogin 学员登录
     * @param sStudentID 学员编号  统一编号
     * @param sStudentsCoach 当前教练编号  统一编号
     * @param nTrainObject 培训项目 1-场地模拟，2-场地实操，3-道路模拟，4-道路实操
     * @return  0：本机正常登录（不代表登录平台成功），-1：登录失败，-2：传入参数有误*/
    public int UI_StudentLogin(String sStudentID, String sStudentsCoach, int nTrainObject)
    {
        if(false)//TODO 其他非法输入检测
        {
            return -2;
        }
        return 0;
    }

    /** UI_StudentLogout 学员登出
     * @param sStudentID 学员编号  统一编号
     * @param nTrainObject 培训项目 1-场地模拟，2-场地实操，3-道路模拟，4-道路实操
     * @return  0：本机正常登出（不代表登出平台成功），-1 登出失败，-2 传入参数有误*/
    public int UI_StudentLogout(String sStudentID,int nTrainObject)
    {
        if(false)//TODO 其他非法输入检测
        {
            return -2;
        }
        return 0;
    }

    /** TR_GetNetServerPara 获取服务器网络参数
     * @return  0：正常获取返回NetServerPara_S对象引用，null：获取失败*/
    public NetServerPara_S  UI_GetNetServerPara()
    {
        return (NetServerPara_S)m_NetServerPara.clone();
    }

    /** UI_SetNetServerPara 设置服务器网络参数
     * @return  0：设置成功，-1：获取失败，-2：传入参数有误，-4及以下：其他错误*/
    public int UI_SetNetServerPara(NetServerPara_S para)
    {
        //m_NetServerPara = para;

        m_NetServerPara.sMainServerDomain = para.sMainServerDomain;
        m_NetServerPara.lMainServerPort =  para.lMainServerPort;
      //  m_NetServerPara = para.clone();
        return 0;
    }

    /** UI_GetSysStatus 获取系统状态参数
     * @return  0：正常获取返回SysStatus_S对象引用，null：获取失败*/
    public SysStatus_S UI_GetSysStatus()
    {
        //m_API.CB.TRCallBack(MSG_SysStatus, m_Status, 0, null);
        SysStatus_S obj = (SysStatus_S)m_Status.clone();
        return obj;
    }


    public int SetSystemInfo()
    {
        m_SysInfo.sAPPVersion = "1.0.0.Beta";
        m_SysInfo.sOBDVersion = "1.0.0";
        m_SysInfo.sSIMNo = "13122223333";
        m_SysInfo.sPlatformID = "666666";
        m_SysInfo.sOrganizationID="12345";
        m_SysInfo.sTerminnalID = "88888888";
        return 0;
    }
    /** UIGetInfo 获取系统状态参数
     * @return  0：正常获取返回SysInfo_S对象引用，null：获取失败*/
    public SysInfo_S UI_GetInfo()
    {
        SysInfo_S Sys = (SysInfo_S)m_SysInfo.clone();
        return Sys;
    }

    /** UI_GetFaceRecognizeAccount获取脸部识别登录账户
     * @return  0：正常获取返回FaceRecognizeAccount_S对象引用，null：获取失败*/
    public FaceRecognizeAccount_S UI_GetFaceRecognizeAccount()
    {
        FaceRecognizeAccount_S Face = m_API.new FaceRecognizeAccount_S();
        Face.sApiSecret = m_Face.sApiSecret;
        Face.sApiKey = m_Face.sApiKey;
        return Face;
    }



}


