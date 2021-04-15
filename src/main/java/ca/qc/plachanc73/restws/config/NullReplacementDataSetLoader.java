package ca.qc.plachanc73.restws.config;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;

public class NullReplacementDataSetLoader extends FlatXmlDataSetLoader {

	@Override
	protected IDataSet createDataSet(Resource resource) throws Exception {
		return createReplacementDataSet(super.createDataSet(resource));
	}

	private ReplacementDataSet createReplacementDataSet(IDataSet dataSet) {
		ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);

		// Configure the replacement dataset to replace '[NULL]' strings with null.
		replacementDataSet.addReplacementObject("[NULL]", null);

		return replacementDataSet;
	}
}