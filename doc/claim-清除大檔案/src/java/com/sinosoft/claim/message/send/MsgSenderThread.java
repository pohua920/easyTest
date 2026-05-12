package com.sinosoft.claim.message.send;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.claim.message.vo.MsgSenderDto;

/**
 * <p>
 * Title: 即时消息
 * </p>
 * <p>
 * Description: 即时消息
 * </p>
 * @author 中科软
 * @version
 */
public class MsgSenderThread extends Thread {
	/** 消息发送实体类 */
	private MsgSenderDto msgSenderDto;
	/** 日志 */
	private static final Log logger = LogFactory.getLog(MsgSenderThread.class);

	/**
	 * 構造方法
	 * @param msgSenderDto 發送消息實體類
	 * @throws Exception
	 */
	public MsgSenderThread(MsgSenderDto msgSenderDto) {
		super();
		this.msgSenderDto = msgSenderDto;

	}

	public void run() {
		try {
			// System.out.println("线程开始了------------------------------------------------》》》》》》");
			String href = msgSenderDto.getTaskLink();
			String content = "";
			if (msgSenderDto.getBusinessType().equals("T")) {
				content = msgSenderDto.getSendUserName() + ": ";
				if ("A01".equals(msgSenderDto.getRiskCode())) {
					content += "強制險";
				} else if ("B01".equals(msgSenderDto.getRiskCode())) {
					content += "任意險";
				}
				content += "要保單號:<a href='#' value='" + href + "' onclick='return dealTask(this);'>" + msgSenderDto.getBusinessNo() + "</a>";
				if ("up".equals(msgSenderDto.getUpDownFlag())) {
					content += "提交高階。";
				} else if ("down".equals(msgSenderDto.getUpDownFlag())) {
					content += "下發修改。";
				} else if ("pass".equals(msgSenderDto.getUpDownFlag())) {
					content += "核保通過";
				} else {
					content += "等待核保。";
				}
			} else if (msgSenderDto.getBusinessType().equals("E")) {
				content = msgSenderDto.getSendUserName() + ": ";
				if ("A01".equals(msgSenderDto.getRiskCode())) {
					content += "強制險";
				} else if ("B01".equals(msgSenderDto.getRiskCode())) {
					content += "任意險";
				}
				content += "批單號:<a href='#' value='" + href + "' onclick='return dealTask(this);'>" + msgSenderDto.getBusinessNo() + "</a>";
				if ("up".equals(msgSenderDto.getUpDownFlag())) {
					content += "提交高階。";
				} else if ("down".equals(msgSenderDto.getUpDownFlag())) {
					content += "下發修改。";
				} else if ("pass".equals(msgSenderDto.getUpDownFlag())) {
					content += "核保通過";
				} else {
					content += "等待核保。";
				}
			} else if (msgSenderDto.getBusinessType().equals("C") || msgSenderDto.getBusinessNo().equals("Y")) {
				content = msgSenderDto.getSendUserName() + ": ";
				if ("A01".equals(msgSenderDto.getRiskCode())) {
					content += "強制險";
				} else if ("B01".equals(msgSenderDto.getRiskCode())) {
					content += "任意險";
				}
				content += "業務號:<a href='#' value='" + href + "' onclick='return dealTask(this);'>" + msgSenderDto.getBusinessNo() + "</a>";
				if ("up".equals(msgSenderDto.getUpDownFlag())) {
					content += "提交高階。";
				} else if ("down".equals(msgSenderDto.getUpDownFlag())) {
					content += "下發修改。";
				} else if ("pass".equals(msgSenderDto.getUpDownFlag())) {
					content += "核賠通過";
				} else {
					content += "等待核賠。";
				}
			} else {
				return;
			}
			Sender sender = new Sender();
			// System.out.println("即时通讯--发送者登陆，发送人："+msgSenderDto.getSendUserCode());
			sender.connect(msgSenderDto.getSendUserCode(), "1111");
			// System.out.println("即时通讯--登陆成功");
			// System.out.println("接受者个数---------------------》"+msgSenderDto.getRcverUser().size());
			for (int i = 0; i < msgSenderDto.getRcverUser().size(); i++) {
				String target = (String) msgSenderDto.getRcverUser().get(i);
				logger.info("发送消息：：：：：" + content);
				// System.out.println("接受者---------------------》"+target);
				sender.sendMsg(target, content);
			}
			sender.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
