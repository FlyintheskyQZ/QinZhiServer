package seu.qz.qzserver.mybatismapper;

import seu.qz.qzserver.entity.ExperimentResult;

public interface ExperimentResultMapper {

    public ExperimentResult getExperimentResultById(Integer id);

    public int updateExperimentResult(ExperimentResult result);

    public int addExperimentResult(ExperimentResult result);

    public int deleteExperimentResultById(Integer id);
}
