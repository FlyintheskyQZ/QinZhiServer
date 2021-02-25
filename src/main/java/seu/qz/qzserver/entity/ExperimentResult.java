package seu.qz.qzserver.entity;

import java.util.List;

/**
 * 实验结果类：
 *      全局实验结果id，商家实验结果id，仪器id，用户id，完成订单id，实验材料id，实验过程中的氧浓度节点集、燃烧时间节点集、燃烧长度节点集、判断结果集，
 *      浓度节点集字符串，燃烧时间节点集字符串，燃烧长度节点集字符串，燃烧结果节点集字符串，计算取K值，步长值，测量方差，补偿是否合适，最终测量氧指数
 */
public class ExperimentResult {

    private Integer global_result_id;
    private Integer factory_result_id;
    private Integer device_id;
    private Integer user_id;
    private Integer order_id;

    private Integer light_way;
    private Integer material_id;
    private List<Float> oxygen_cons;
    private List<Integer> burning_times;
    private List<Integer> burning_lengths;
    private List<Character> burning_judgements;
    private Integer part1_length;
    private String o_cons_string;
    private String b_times_string;
    private String b_lengths_string;
    private String b_judge_string;
    private float calculate_k;
    private float step_value;
    private float variance;
    private boolean step_isSuitable;
    private float final_LOI;

    public ExperimentResult() {
        super();
    }

    public ExperimentResult(Integer global_result_id, Integer factory_result_id, Integer device_id, Integer user_id,
                            Integer order_id, Integer material_id, List<Float> oxygen_cons,
                            List<Integer> burning_times, List<Integer> burning_lengths, List<Character> burning_judgements,
                            String o_cons_string, String b_times_string, String b_lengths_string, String b_judge_string,
                            float calculate_k, float step_value, float variance, boolean step_isSuitable, float final_LOI) {
        this.global_result_id = global_result_id;
        this.factory_result_id = factory_result_id;
        this.device_id = device_id;
        this.user_id = user_id;
        this.order_id = order_id;
        this.material_id = material_id;
        this.oxygen_cons = oxygen_cons;
        this.burning_times = burning_times;
        this.burning_lengths = burning_lengths;
        this.burning_judgements = burning_judgements;
        this.o_cons_string = o_cons_string;
        this.b_times_string = b_times_string;
        this.b_lengths_string = b_lengths_string;
        this.b_judge_string = b_judge_string;
        this.calculate_k = calculate_k;
        this.step_value = step_value;
        this.variance = variance;
        this.step_isSuitable = step_isSuitable;
        this.final_LOI = final_LOI;
    }

    @Override
    public String toString() {
        return "ExperimentResult{" +
                "global_result_id=" + global_result_id +
                ", factory_result_id=" + factory_result_id +
                ", device_id=" + device_id +
                '}';
    }

    public Integer getPart1_length() {
        return part1_length;
    }

    public void setPart1_length(Integer part1_length) {
        this.part1_length = part1_length;
    }

    public Integer getLight_way() {
        return light_way;
    }

    public void setLight_way(Integer light_way) {
        this.light_way = light_way;
    }

    public String getB_lengths_string() {
        return b_lengths_string;
    }

    public void setB_lengths_string(String b_lengths_string) {
        this.b_lengths_string = b_lengths_string;
    }

    public String getO_cons_string() {
        return o_cons_string;
    }

    public void setO_cons_string(String o_cons_string) {
        this.o_cons_string = o_cons_string;
    }

    public String getB_times_string() {
        return b_times_string;
    }

    public void setB_times_string(String b_times_string) {
        this.b_times_string = b_times_string;
    }

    public String getB_judge_string() {
        return b_judge_string;
    }

    public void setB_judge_string(String b_judge_string) {
        this.b_judge_string = b_judge_string;
    }

    public Integer getGlobal_result_id() {
        return global_result_id;
    }

    public void setGlobal_result_id(Integer global_result_id) {
        this.global_result_id = global_result_id;
    }

    public Integer getFactory_result_id() {
        return factory_result_id;
    }

    public void setFactory_result_id(Integer factory_result_id) {
        this.factory_result_id = factory_result_id;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(Integer material_id) {
        this.material_id = material_id;
    }

    public List<Float> getOxygen_cons() {
        return oxygen_cons;
    }

    public void setOxygen_cons(List<Float> oxygen_cons) {
        this.oxygen_cons = oxygen_cons;
    }

    public List<Integer> getBurning_times() {
        return burning_times;
    }

    public void setBurning_times(List<Integer> burning_times) {
        this.burning_times = burning_times;
    }

    public List<Integer> getBurning_lengths() {
        return burning_lengths;
    }

    public void setBurning_lengths(List<Integer> burning_lengths) {
        this.burning_lengths = burning_lengths;
    }

    public List<Character> getBurning_judgements() {
        return burning_judgements;
    }

    public void setBurning_judgements(List<Character> burning_judgements) {
        this.burning_judgements = burning_judgements;
    }

    public float getCalculate_k() {
        return calculate_k;
    }

    public void setCalculate_k(float calculate_k) {
        this.calculate_k = calculate_k;
    }

    public float getStep_value() {
        return step_value;
    }

    public void setStep_value(float step_value) {
        this.step_value = step_value;
    }

    public float getVariance() {
        return variance;
    }

    public void setVariance(float variance) {
        this.variance = variance;
    }

    public boolean isStep_isSuitable() {
        return step_isSuitable;
    }

    public void setStep_isSuitable(boolean step_isSuitable) {
        this.step_isSuitable = step_isSuitable;
    }

    public float getFinal_LOI() {
        return final_LOI;
    }

    public void setFinal_LOI(float final_LOI) {
        this.final_LOI = final_LOI;
    }
}
