/**
 * 
 */
package space;

import java.util.ArrayList;
import java.util.List;

import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefactoring;
import entity.MetaphorCode;
import unalcol.random.integer.IntUniform;
import unalcol.types.collection.bitarray.BitArray;

/**
 * @author Daavid
 *
 */
public class GeneratingRefactorRMMO extends GeneratingRefactor {

	/* (non-Javadoc)
	 * @see entity.MappingRefactor#mappingRefactor(java.lang.String, unalcol.types.collection.bitarray.BitArray, entity.MetaphorCode)
	 */
	
	protected Refactoring type = Refactoring.replaceMethodObject;
	@Override
	public OBSERVRefactoring generatingRefactor( MetaphorCode code ) {
		boolean feasible ;
		List<OBSERVRefParam> params;
		IntUniform g = new IntUniform ( code.getMapClass().size() );
		TypeDeclaration sysType_src;
		List<String> value_mtd;
		String mtdName = null;
		
		do{
			feasible = true;
			params = new ArrayList<OBSERVRefParam>();
			
			//Creating the OBSERVRefParam for the src class
			sysType_src =  code.getMapClass().get( g.generate() );
			List<String> value_src  = new ArrayList<String>();
			value_src.add(sysType_src.getQualifiedName());
			params.add(new OBSERVRefParam("src", value_src));
				
			//Creating the OBSERVRefParam for the mtd class
			value_mtd  = new ArrayList<String>();
			
			if( !code.getMethodsFromClass(sysType_src).isEmpty() ){
				IntUniform numMtdObs = new IntUniform ( code.getMethodsFromClass(sysType_src).size() );
				mtdName = (String) code.getMethodsFromClass(sysType_src).toArray()
						 [numMtdObs.generate()];
				value_mtd.add(mtdName);
				
				//verification of method not constructor
				if(value_mtd.get(0).equals(sysType_src.getName()))
					feasible = false;
				
				if(feasible){
					//Override verification parents 
					if( !code.getBuilder().getParentClasses().get( sysType_src.getQualifiedName()).isEmpty() ){
						for( TypeDeclaration clase : code.getBuilder().getParentClasses().get( sysType_src.getQualifiedName()) ){
							for( String method : code.getMethodsFromClass(clase) ){
								if( method.equals( value_mtd.get(0) ) ){
									feasible = false;
									break;
								}
							}
						}
					}
					if(feasible){
						//Override verification children
						if( !code.getBuilder().getChildClasses().get( sysType_src.getQualifiedName()).isEmpty() ){
							for( TypeDeclaration clase : code.getBuilder().getChildClasses().get( sysType_src.getQualifiedName()) ){
								for( String method : code.getMethodsFromClass(clase) ){
									if( method.equals( value_mtd.get(0) ) ){
										feasible = false;
										break;
									}
								}
							}
						}
					}
				}
				
				
				params.add(new OBSERVRefParam("mtd", value_mtd));
			}else{
				feasible = false;
			}
		}while( !feasible );
		
		//Creating the OBSERVRefParam for the tgt
		//This Target Class is not inside metaphor
		List<String> value_tgt  = new ArrayList<String>();
		value_tgt.add( sysType_src.getPack() + mtdName + "|N");
		params.add(new OBSERVRefParam("tgt", value_tgt));
		code.addClasstoHash(sysType_src.getPack(), mtdName + "|N");
		
		return new OBSERVRefactoring(type.name(),params,feasible);
	}

}