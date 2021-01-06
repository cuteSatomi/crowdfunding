package com.zzx.crowd.mvc.controller;

import com.zzx.crowd.entity.Menu;
import com.zzx.crowd.service.api.MenuService;
import com.zzx.crowd.util.ResultEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * @date 2021-01-05 21:51
 */
@Controller
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 根据主键id删除菜单
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("menu/remove.json")
    public ResultEntity<String> removeMenu(@RequestParam("id") Integer id){
        menuService.removeMenu(id);
        return ResultEntity.successWithoutData();
    }

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping("menu/update.json")
    public ResultEntity<String> updateMenu(Menu menu){
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();
    }

    /**
     * 保存菜单
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping("menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }

    /**
     * 得到所有的菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTree() {

        // 得到所有menu集合
        List<Menu> menuList = menuService.getAll();
        // 对得到的集合做一些处理
        // 首先需要构建menuMap
        Map<Integer, Menu> menuMap = new HashMap<>();

        // 遍历menuList，填充menuMap
        menuList.forEach(menu -> menuMap.put(menu.getId(), menu));

        // 此时的map中的key都是menu的id，值是menu，方便下面的查找
        // 声明一个root变量来存储找到的根节点
        Menu root = null;
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            // 如果找到根节点，则无需继续判断其有没有父节点，continue进入下一次循环
            if (pid == null) {
                root = menu;
                continue;
            }
            // 根据pid在map中查询到该节点的父节点
            Menu father = menuMap.get(pid);
            // 将该节点设置为他的父亲节点的子节点
            father.getChildren().add(menu);
        }
        // 返回root节点，其实就是返回了所有的menu，因为其他的menu都套娃在他的各个子节点中
        return ResultEntity.successWithData(root);
    }
}
