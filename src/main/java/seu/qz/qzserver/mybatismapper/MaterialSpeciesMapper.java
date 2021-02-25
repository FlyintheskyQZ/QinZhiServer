package seu.qz.qzserver.mybatismapper;

import seu.qz.qzserver.entity.MaterialSpecies;

public interface MaterialSpeciesMapper {

    public MaterialSpecies getMaterialById(Integer id);

    public int updateMaterial(MaterialSpecies material);

    public int addMaterial(MaterialSpecies material);

    public int deleteMaterialById(Integer id);
}
