package seu.qz.qzserver.entity;


/**
 * 实验材料对象类：
 *      实验材料id，实验材料名称，实验材料种类，实验测量方法类
 * 暂时决定不用！！！！！！！！！！！！！
 */
public class MaterialSpecies {

    private Integer material_id;
    private String material_name;
    private Integer material_type;
    private Integer measure_type;
    private String extra_explanation;

    public MaterialSpecies() {
        super();
    }

    public MaterialSpecies(Integer material_id, String material_name, Integer material_type, Integer measure_type) {
        this.material_id = material_id;
        this.material_name = material_name;
        this.material_type = material_type;
        this.measure_type = measure_type;
    }

    @Override
    public String toString() {
        return "MaterialSpecies{" +
                "material_id=" + material_id +
                ", material_name='" + material_name + '\'' +
                ", material_type=" + material_type +
                ", measure_type=" + measure_type +
                '}';
    }

    public String getExtra_explanation() {
        return extra_explanation;
    }

    public void setExtra_explanation(String extra_explanation) {
        this.extra_explanation = extra_explanation;
    }

    public Integer getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(Integer material_id) {
        this.material_id = material_id;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public Integer getMaterial_type() {
        return material_type;
    }

    public void setMaterial_type(Integer material_type) {
        this.material_type = material_type;
    }

    public Integer getMeasure_type() {
        return measure_type;
    }

    public void setMeasure_type(Integer measure_type) {
        this.measure_type = measure_type;
    }
}
