package cn.seckill.web;

import cn.seckill.dto.Exposer;
import cn.seckill.dto.SeckillExecution;
import cn.seckill.dto.SeckillResult;
import cn.seckill.entity.Seckill;
import cn.seckill.enums.SeckillStatEnum;
import cn.seckill.exception.RepeatKillException;
import cn.seckill.exception.SeckillCloseException;
import cn.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 秒杀接口后端控制器
 * Created by Administrator on 2018/9/10 0010.
 */
@Controller
@RequestMapping("/seckill")//模块 url:/模块/资源/{id}/细分
public class SeckillController {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Resource(name = "seckillServiceImpl")
    private SeckillService seckillService;

    /**
     * 获取列表页
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
          List<Seckill> seckillList= seckillService.getAllSeckill();
          model.addAttribute("seckillList",seckillList);
          //list.jsp+model=ModelAndView
        return  "/list";
    }

    /**
     * 秒杀详情
     * @return
     */
    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if (seckillId==null){
            return "redirect:/seckill/list";
        }
        Seckill seckill= seckillService.querySeckillById(seckillId);
        if (seckill==null){
            return "redirect:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "/detail";
    }

    /**
     * ajax,json
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        try {
            Exposer exposer=seckillService.exportSeckillUrl(seckillId);
            return new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new SeckillResult<Exposer>(false,e.getMessage());
        }
    }


    /**
     * 执行秒杀
     * @param seckillId
     * @param md5
     * @param userPhone
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution>  execution(
            @PathVariable("seckillId") Long seckillId,
            @PathVariable("md5") String md5,
            @CookieValue(value = "killPhone",required = false) Long userPhone){

        //SpringMVC valid验证信息
        if (userPhone==null){
            return new SeckillResult<SeckillExecution>(false,"未登陆");
        }
        try {
            SeckillExecution execution=  seckillService.executeSeckill(seckillId,userPhone,md5);
            return new SeckillResult<SeckillExecution>(true,execution);
        }catch ( SeckillCloseException e1){
            //秒杀关闭
            logger.error(e1.getMessage(),e1);
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        }catch (RepeatKillException e2){
            //重复秒杀
            logger.error(e2.getMessage(),e2);
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        }catch (Exception e){
            //内部错误
            logger.error(e.getMessage(),e);
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        }
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now=new Date();
                return new SeckillResult<Long>(true,now.getTime());
    }

}
