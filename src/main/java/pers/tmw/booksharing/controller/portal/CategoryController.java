package pers.tmw.booksharing.controller.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.tmw.booksharing.common.Const;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.User;
import pers.tmw.booksharing.service.ICategoryService;

import javax.servlet.http.HttpSession;

/**
 * Created by tmw090906 on 2017/6/8.
 * 前台书分类接口
 */
@RequestMapping("/category/")
@Controller
public class CategoryController {


    @Autowired
    private ICategoryService iCategoryService;


    /**
     * 树形数据结构列表接口
     * @param session
     * @return
     */
    @RequestMapping("get_tree_category.do")
    @ResponseBody
    public ServerResponse getTreeCategory(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录,请登录");
        }
        return iCategoryService.getTreeCategory();
    }

}
