package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import member.bean.Member;
import service.MemberService;
import service.PostService;

@WebServlet("/adminMemberController")
public class AdminMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		// 私密金鑰檔案可以儲存在專案以外
		// File file = new File("/path/to/firsebase-java-privateKey.json");
		// 私密金鑰檔案也可以儲存在專案WebContent目錄內，私密金鑰檔名要與程式所指定的檔名相同
		if (NotificationController.firebaseApp == null) {
			try (InputStream in = getServletContext().getResourceAsStream("/firebaseServerKey.json")) {
				FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(in))
						.build();
				NotificationController.firebaseApp = FirebaseApp.initializeApp(options);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		JsonObject req;
		JsonObject resp=new JsonObject();
		MemberService memberService = new MemberService();
		try (BufferedReader reader = request.getReader(); PrintWriter pw = response.getWriter()) {
			req = gson.fromJson(reader, JsonObject.class);
//			System.out.println("客戶端的請求:" + req);
			if (req.get("action").getAsString().equals("getAllMember")) {
				List<Member>members=new ArrayList<Member>();
				for(Member member:memberService.selectAll()) {
					if(member.getRole()!=0) {
						members.add(member);
					}
				}	
				pw.print(gson.toJson(members));
			}
			else if(req.get("action").getAsString().equals("updateMember")) {
				Member member=gson.fromJson(req.get("member").getAsString(), Member.class);
//				System.out.println("比對的電話:"+member.getPhone());
				Member selectMember=memberService.selectByPhone(member.getPhone());
				if(selectMember!=null) {
					//非同一人
					if(selectMember.getMemberId()!=member.getMemberId()) {
						resp.addProperty("pass", 2); //電話號碼已被使用
					}
					//Integer為物件使用==會比對記憶體位址需比較值須使用equal
					else if(selectMember.getRole()==member.getRole()&&selectMember.getType()==member.getType()&&selectMember.getPhone().equals(member.getPhone())) {
						resp.addProperty("pass", 3); //資料無變更
					}
					else if(memberService.adminuUpdate(member)>0) {
						resp.addProperty("pass", 0); //成功
						String memberToken=memberService.selectById(member.getMemberId()).getToken();
						if(memberToken!=null) {
						JsonObject notificaitonFCM = new JsonObject();
						notificaitonFCM.addProperty("title", "系統通知");
						notificaitonFCM.addProperty("body", "您的個人資料已更新");
						NotificationController.sendSingleFcm(notificaitonFCM, memberToken);
						}
					}
					else {
						resp.addProperty("pass", 1); //更新失敗
					}
				}
				//更新電話
				else if(selectMember==null) {
					if(memberService.adminuUpdate(member)>0) {
						resp.addProperty("pass", 0); //成功
						String memberToken=memberService.selectById(member.getMemberId()).getToken();
						if(memberToken!=null) {
						JsonObject notificaitonFCM = new JsonObject();
						notificaitonFCM.addProperty("title", "系統通知");
						notificaitonFCM.addProperty("body", "您的個人資料已更新");
						NotificationController.sendSingleFcm(notificaitonFCM, memberToken);
						}
					}
					else {
						resp.addProperty("pass", 1); //更新失敗
					}
				}
//				System.out.println("伺服器的回應:"+resp.toString());
				pw.print(resp.toString());
				
			}
			
			else if(req.get("action").getAsString().equals("getApplyMember")) {
				List<Member> members=memberService.selectApplyLandlordMember();
				//搜尋錯誤
				if(members==null) {
					
				}
				else if(members.size()>=0) {
					pw.print(gson.toJson(members));
				}
			
			}
			else if(req.get("action").getAsString().equals("applyMemberResult")){
				Member member=gson.fromJson(req.get("member").getAsString(), Member.class);
				if(memberService.adminUpdatePass(member)>0){
					resp.addProperty("result", true);
				}
				else {
					resp.addProperty("result", false);
				}
				pw.print(resp.toString());
			}
			
	}
		 catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		

}
