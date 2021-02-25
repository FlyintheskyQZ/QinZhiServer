package seu.qz.qzserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import seu.qz.qzserver.entity.AppCustomer;
import seu.qz.qzserver.entity.LOIInstrument;
import seu.qz.qzserver.service.LOIInstrumentService;
import seu.qz.qzserver.util.OrderComparatorUtils;

import java.util.List;

@Controller
public class LOIInstrumentController {

    @Autowired
    LOIInstrumentService instrumentService;

    @ResponseBody
    @RequestMapping("/getInstrumentsById")
    public List<LOIInstrument> getInstrumentsById(String saler_id){
        if(saler_id == null || saler_id.isEmpty()){
            return null;
        }
        int id = Integer.parseInt(saler_id);
        System.out.println("instruments id is" + id);
        List<LOIInstrument> loiInstruments = instrumentService.getInstrumentsById(id);
        if(loiInstruments != null && loiInstruments.size() > 1){
            OrderComparatorUtils.adjustInstrumentsByRelatedOrder(loiInstruments);
        }
        System.out.println(loiInstruments.size());
        return loiInstruments;
    }

    @ResponseBody
    @RequestMapping(value = "/registerLOIInstrument", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String registerLOIInstrument(@RequestBody LOIInstrument instrument){
        System.out.println(instrument.toString());
        if(instrument == null){
            return "Failed";
        }
        if(instrumentService.addNewInstrument(instrument)){
            return "Success:" + instrument.getDevice_id();
        }else {
            return "Failed";
        }
    }


}
