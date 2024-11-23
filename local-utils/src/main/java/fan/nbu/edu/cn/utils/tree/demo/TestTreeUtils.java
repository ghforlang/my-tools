package fan.nbu.edu.cn.utils.tree.demo;

import com.alibaba.fastjson.JSON;
import fan.nbu.edu.cn.utils.tree.TreeUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/20-10:17
 * @since 1.0
 */
public class TestTreeUtils {
    private static final List<MenuVo> menuList;
    static {
        MenuVo menu0 = new MenuVo(0L, -1L,0);
        MenuVo menu1 = new MenuVo(1L, 0L,4);
        MenuVo menu2 = new MenuVo(2L, 0L,9);
        MenuVo menu3 = new MenuVo(3L, 1L,10);
        MenuVo menu4 = new MenuVo(4L, 1L,43);
        MenuVo menu5 = new MenuVo(5L, 2L,9);
        MenuVo menu6 = new MenuVo(6L, 2L,4);
        MenuVo menu7 = new MenuVo(7L, 3L,5);
        MenuVo menu8 = new MenuVo(8L, 3L,7);
        MenuVo menu9 = new MenuVo(9L, 4L,8);
        //基本数据
        menuList = Arrays.asList(menu0,menu1, menu2,menu3,menu4,menu5,menu6,menu7,menu8,menu9);
    }

    public static void main(String[] args) {
        //合成树
        List<MenuVo> tree= TreeUtils.makeTree(menuList, x->x.getPId()==-1L,(x, y)->x.getId().equals(y.getPId()), MenuVo::setSubMenus);
        System.out.println("树合成结果:");
        System.out.println(JSON.toJSONString(tree));

        
        //先序
        StringBuffer preStr=new StringBuffer();
        TreeUtils.forPreOrder(tree,x-> preStr.append(x.getId().toString()),MenuVo::getSubMenus);
        System.out.println("先序遍历结果: " + "0137849256".equals(preStr.toString()));
        System.out.println("preStr:" + preStr);

        //层序
        StringBuffer levelStr=new StringBuffer();
        TreeUtils.forLevelOrder(tree,x-> levelStr.append(x.getId().toString()),MenuVo::getSubMenus);
        System.out.println("层序遍历结果: " + "0123456789".equals(levelStr.toString()));
        System.out.println("levelStr:" + levelStr);

        //后序
        StringBuffer postStr=new StringBuffer();
        TreeUtils.forPostOrder(tree,x-> postStr.append(x.getId().toString()),MenuVo::getSubMenus);
        System.out.println("后序遍历结果: " + "7839415620".equals(postStr.toString()));
        System.out.println("postOrder:" + postStr);

        // 树排序
        List<MenuVo> sortTree= TreeUtils.sort(tree, (x,y)->y.getRank().compareTo(x.getRank()), MenuVo::getSubMenus);
        System.out.println("树排序结果:");
        System.out.println(JSON.toJSONString(sortTree));

        // 树打平
        List<MenuVo> flatTree = TreeUtils.flat(tree,MenuVo::getSubMenus,x -> x.setSubMenus(null));
        System.out.println("树打平结果:");
        System.out.println(JSON.toJSONString(flatTree));
    }

}
