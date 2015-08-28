/**
 * 
 */
package entity;

import java.util.ArrayList;
import java.util.List;

import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefactoring;
import entity.MappingRefactor.Refactoring;
import unalcol.types.collection.bitarray.BitArray;

/**
 * @author Daavid
 *
 */
public class MappingRefactorEC extends MappingRefactor {

	/* (non-Javadoc)
	 * @see entity.MappingRefactor#mappingRefactor(java.lang.String, unalcol.types.collection.bitarray.BitArray, entity.MetaphorCode)
	 */
	
	protected Refactoring type = Refactoring.extractClass;
	@Override
	public OBSERVRefactoring mappingRefactor(QubitRefactor genome, MetaphorCode code) {
		// TODO Auto-generated method stub
		
		List<OBSERVRefactoring> subRefs = new ArrayList<OBSERVRefactoring>();
		subRefs.add(mappingRefactorMF(genome, code, "TgtClassEC"));
		subRefs.add(mappingRefactorMM(genome, code, "TgtClassEC"));
		
		return new OBSERVRefactoring(type.name(),null,subRefs);
	}
	
	public OBSERVRefactoring mappingRefactorMF(QubitRefactor genome, MetaphorCode code, String newClass) {
		// TODO Auto-generated method stub
		
		List<OBSERVRefParam> params = new ArrayList<OBSERVRefParam>();		

		
		//Creating the OBSERVRefParam for the src class
		int	numSrcObs = genome.getNumberGenome(genome.getGenSRC());
		TypeDeclaration sysType_src = code.getMapClass().get(numSrcObs % 
					code.getMapClass().size());
		List<String> value_src  = new ArrayList<String>();
		value_src.add(sysType_src.getQualifiedName());
		params.add(new OBSERVRefParam("src", value_src));
		
		//Creating the OBSERVRefParam for the fld field
		int numFldObs = genome.getNumberGenome(genome.getGenFLD());
		List<String> value_fld  = new ArrayList<String>();
		String fldName = (String) code.getFieldsFromClass(sysType_src).toArray()[numFldObs
		               % code.getFieldsFromClass(sysType_src).size()];
		value_fld.add(fldName);
		params.add(new OBSERVRefParam("fld", value_fld));
		
		//Creating the OBSERVRefParam for the tgt
		//This Target Class is not inside metaphor
		List<String> value_tgt  = new ArrayList<String>();
		value_tgt.add( sysType_src.getPack() + newClass + "|N");
		params.add(new OBSERVRefParam("tgt", value_tgt));
		code.addClasstoHash(sysType_src.getPack(), newClass + "|N");
		
		return new OBSERVRefactoring(type.name(),params);
	}
	
	public OBSERVRefactoring mappingRefactorMM(QubitRefactor genome, MetaphorCode code,String newClass) {
		// TODO Auto-generated method stub
		List<OBSERVRefParam> params = new ArrayList<OBSERVRefParam>();
		
		//Creating the OBSERVRefParam for the src class
		int numSrcObs = genome.getNumberGenome(genome.getGenSRC());
		TypeDeclaration sysType_src =  code.getMapClass().get(numSrcObs % 
				code.getMapClass().size());
		List<String> value_src  = new ArrayList<String>();
		value_src.add(sysType_src.getQualifiedName());
		params.add(new OBSERVRefParam("src", value_src));
		
		//Creating the OBSERVRefParam for the mtd class
		int numMtdObs = genome.getNumberGenome(genome.getGenMTD());
		List<String> value_mtd  = new ArrayList<String>();
		value_mtd.add((String) code.getMethodsFromClass(sysType_src).toArray()[numMtdObs
		     		  % code.getMethodsFromClass(sysType_src).size()]);
		params.add(new OBSERVRefParam("mtd", value_mtd));
		
		//Creating the OBSERVRefParam for the tgt
		//This Target Class is not inside metaphor
		List<String> value_tgt  = new ArrayList<String>();
		value_tgt.add( sysType_src.getPack() + newClass + "|N");
		params.add(new OBSERVRefParam("tgt", value_tgt));
		code.addClasstoHash(sysType_src.getPack(), newClass + "|N");
		
		
		return new OBSERVRefactoring(type.name(),params);

	}

	/* (non-Javadoc)
	 * @see entity.MappingRefactor#mappingParams()
	 */
	@Override
	public List<OBSERVRefParam> mappingParams() {
		// TODO Auto-generated method stub
		return null;
	}

}
