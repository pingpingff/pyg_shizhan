package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<Brand> findAll() {
        List<Brand> list = brandService.findAll();
        return list;
    }

    /**
     * 品牌分页查询
     * @param page  当前页
     * @param rows  每页查询多少条数据
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(Integer page, Integer rows) {
        PageResult result = brandService.findPage(null, page, rows);
        return result;
    }

    /**
     * 添加品牌数据
     * @param brand
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody  Brand brand) {
        try {
            brandService.add(brand);
            return new Result(true, "添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败!");
        }
    }

    /**
     * 根据id查询品牌对象
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Brand findOne(Long id) {
        Brand one = brandService.findOne(id);
        return one;
    }

    /**
     * 保存修改
     * @param brand
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand) {
        try {
            brandService.update(brand);
            return new Result(true, "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败!");
        }
    }

    /**
     * 删除选中的数据
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            brandService.delete(ids);
            return new Result(true, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Brand brand, Integer page, Integer rows) {
        PageResult result = brandService.findPage(brand, page, rows);
        return result;
    }

    /**
     * 获取模板下拉选择框所需要的数据
     * [{id:1,text:xxx},{id:2, text: asdfasdfsa}]
     * @return
     */
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        List<Map> list = brandService.selectOptionList();
        return list;
    }

    @RequestMapping("/updateStatus")
    public  Result updateStatus(Long[] ids, String status) {
        try {
            if (ids != null) {
                for (Long id : ids) {
                    //1. 更改数据库中商品的审核状态
                    brandService.updateStatus(id, status);

                    //2. 判断商品审核状态是否为1, 审核通过
//                    if ("1".equals(status)) {
//                        //3. 根据商品id, 获取商品详细数据, 放入solr索引库中供前台系统搜索使用
//                        solrManagerService.saveItemToSolr(id);
//                        //4. 根据商品id, 获取商品详细数据, 通过数据和模板生成商品详情页面
//                        Map<String, Object> goodsMap = cmsService.findGoodsData(id);
//                        cmsService.createStaticPage(goodsMap, id);
//                    }
                }
            }
            return new Result(true, "状态修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "状态修改失败!");
        }
    }
}
