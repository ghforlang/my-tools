package fan.nbu.edu.cn.utils.tree.demo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/20-10:16
 * @since 1.0
 */
@Getter
@Setter
public class MenuVo {
    private Long id;
    private Long pId;
    private String name;
    private Integer rank=0;
    private List<MenuVo> subMenus=new ArrayList<>();

    public MenuVo(Long id,Long pId,Integer rank){
        this.id = id;
        this.pId = pId;
        this.rank = rank;
    }

}
