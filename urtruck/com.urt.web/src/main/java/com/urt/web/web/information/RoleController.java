package com.urt.web.web.information;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.Exc.BusiExceptionLog;
import com.urt.Exc.ExceCode;
import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoSsNavigationDto;
import com.urt.dto.LaoSsRoleDto;
import com.urt.dto.LaoSsRoleNavigationDto;
import com.urt.interfaces.authority.LaoSsNavigationService;
import com.urt.interfaces.authority.LaoSsRoleService;
import com.urt.web.common.util.viewTreeBean;

/**
 * 类说明：
 * @author zhaoxy9
 * @date 2016年6月21日 下午2:39:44
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private LaoSsRoleService roleService;
	@Autowired
	private LaoSsNavigationService tfFSsNavigationService;
	@ResponseBody
	@RequestMapping(value="list")
	public  Map<String, Object> roleList(HttpServletRequest req,HttpServletResponse resp,LaoSsRoleDto tfFSsRoleDto) {
		  int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());//开始显示的项
		  int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());//显示多少项
		  int pageNo = (pageStart / pageSize)+1;
		  Map<String, Object> resultMap= roleService.selectRoleByPage(tfFSsRoleDto,pageNo,pageSize);
		 return resultMap;
	}
	@RequestMapping("/roleList")
	public ModelAndView list() {
		ModelAndView mv=new ModelAndView("/role/roleList");
		return mv;
	}
	/**
	 * 功能描述：角色增加跳转
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午3:18:13
	 * @param @return 
	 * @return ModelAndView
	 */
	@RequestMapping("/toAdd")
	public ModelAndView toAdd() {
		ModelAndView mv=new ModelAndView("/role/roleAdd");
		List<viewTreeBean> vBeans=this.getTreeBeans();
		mv.addObject("dto",vBeans);
		return mv;
	}
	/**
	 * 功能描述：角色保存方法
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午3:17:26
	 * @param @param tfFSsRoleDto
	 * @param @param navigationIds
	 * @param @return 
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("/roleAdd")
	public ResultMsg roleAdd(LaoSsRoleDto roleDto,String navigationIds) {
		roleService.saveRole(roleDto,navigationIds);
		ResultMsg msg=new ResultMsg();
		msg.setSuccess(true);
		return msg;
	}
	/**
	 * 功能描述：角色修改跳转
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午3:17:51
	 * @param @param id
	 * @param @return 
	 * @return String
	 */
	@RequestMapping("/toUpdate/{id}")
	public ModelAndView toUpdate(@PathVariable("id")Long id) {
		ModelAndView mv=new ModelAndView("/role/roleUpdate");
		LaoSsRoleDto tfFSsRoleDto=roleService.queryRoleById(id);
		List<LaoSsRoleNavigationDto> rnDtos=roleService.queryListByRoleId(id);
		List<viewTreeBean> vBeans=this.getTreeBeans();
		for (viewTreeBean vBean:vBeans) {
			for (int i = 0; i < rnDtos.size(); i++) {
				LaoSsRoleNavigationDto rnDto=rnDtos.get(i);
				if (vBean.getId().equals(rnDto.getNavigationId())) {
					vBean.setCheck(true);
				}
			}
			for (int j = 0; j < vBean.getViewTreeBeans().size(); j++) {
				for (int k = 0; k < rnDtos.size(); k++) {
					LaoSsRoleNavigationDto rnDto=rnDtos.get(k);
					if (vBean.getViewTreeBeans().get(j).getId().equals(rnDto.getNavigationId())) {
						vBean.getViewTreeBeans().get(j).setCheck(true);
					}
				}
			}
		}
		mv.addObject("roledto", tfFSsRoleDto);
		mv.addObject("vBeans",vBeans);
		return mv;
	}
	/**
	 * 功能描述：角色修改方法
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午6:04:59
	 * @param @param tfFSsRoleDto
	 * @param @param navigationIds
	 * @param @return 
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("roleUpdate")
	public ResultMsg roleUpdate(LaoSsRoleDto roleDto,String navigationIds) {
		roleService.updateRole(roleDto,navigationIds);
		ResultMsg msg=new ResultMsg();
		msg.setSuccess(true);
		return msg;
	}
	/**
	 * 功能描述：菜单展示树
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午3:20:27
	 * @param @return 
	 * @return List<viewTreeBean>
	 */
	public List<viewTreeBean> getTreeBeans() {
		List<LaoSsNavigationDto> tfFSsNavigationDtos=tfFSsNavigationService.queryList();
		List<viewTreeBean> vBeans=new ArrayList<viewTreeBean>();
		for (int i = 0; i < tfFSsNavigationDtos.size(); i++) {
			LaoSsNavigationDto tFSsNavigationDto=tfFSsNavigationDtos.get(i);
			if ("1".equals(tFSsNavigationDto.getUrlLevel())) {
				viewTreeBean vTreeBean=new viewTreeBean();
				List<viewTreeBean> vBeans1=new ArrayList<viewTreeBean>();
				vTreeBean.setId(Long.valueOf(tFSsNavigationDto.getNavigationId()));
				vTreeBean.setName(tFSsNavigationDto.getNavName());
				for ( LaoSsNavigationDto tFSsNavigationDtoj: tfFSsNavigationDtos) {
					if (tFSsNavigationDto.getNavigationId().equals(tFSsNavigationDtoj.getParentId())) {
						viewTreeBean vTreeBean1=new viewTreeBean();
						vTreeBean1.setId(Long.valueOf(tFSsNavigationDtoj.getNavigationId()));
						vTreeBean1.setName(tFSsNavigationDtoj.getNavName());
						vBeans1.add(vTreeBean1);
					}
				}
				vTreeBean.setViewTreeBeans(vBeans1);
				vBeans.add(vTreeBean);
			}
		}
		return vBeans;
	}
	
	@ResponseBody
	@RequestMapping("checkRoleName")
	public ResultMsg checkRoleName(String roleName) {
		ResultMsg msg=new ResultMsg();
		LaoSsRoleDto dto = roleService.queryRoleByRoleName(roleName);
		msg.setSuccess(true);
		if(dto!=null){
			msg.setSuccess(false);
		}
		
		return msg;
	}
	
	/**
	 * 功能说明：
	 * 删除角色信息，若角色已经被账号使用，则不允许删除。
	 * 删除角色的同时删除角色菜单关联表信息。
	 * 
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/roleDelete")
	public ResultMsg deleteRole(Long roleId) {
		ResultMsg msg = new ResultMsg();
		
		try {
			roleService.deleteRole(roleId);
		}
		catch (BusiExceptionLog ex) {
			if (ExceCode.SYS_ROLE_USED.getMsgcode().equals(ex.getMsgCode().getMsgcode())){
				msg.setMsg("角色已经被使用，不能删除。");
			}
			else {
				msg.setMsg("删除角色失败。");
			}
			msg.setSuccess(false);
			return msg;
		}
		
		msg.setSuccess(true);
		msg.setMsg("删除成功。");
		return msg;
	}
}
