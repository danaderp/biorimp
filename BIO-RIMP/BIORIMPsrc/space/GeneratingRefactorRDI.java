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
public class GeneratingRefactorRDI extends GeneratingRefactor {

	/* (non-Javadoc)
	 * @see entity.MappingRefactor#mappingRefactor(java.lang.String, unalcol.types.collection.bitarray.BitArray, entity.MetaphorCode)
	 */
	protected Refactoring type = Refactoring.replaceDelegationInheritance;
	@Override
	public OBSERVRefactoring generatingRefactor( MetaphorCode code ) {
		// TODO Auto-generated method stub
		boolean feasible;
        List<OBSERVRefParam> params;
        IntUniform g = new IntUniform ( code.getMapClass().size() );
        do{
        	feasible = true;
        	params = new ArrayList<OBSERVRefParam>();
			//Creating the OBSERVRefParam for the src class
			TypeDeclaration sysType_src =  code.getMapClass().get( g.generate() );
			List<String> value_src  = new ArrayList<String>();
			value_src.add(sysType_src.getQualifiedName());
			params.add(new OBSERVRefParam("src", value_src));
			
			//Creating the OBSERVRefParam for the tgt
			List<String> value_tgt  = new ArrayList<String>();
			TypeDeclaration sysType_tgt = code.getMapClass().get( g.generate() );
			value_tgt.add( sysType_tgt.getQualifiedName());
			params.add(new OBSERVRefParam("tgt", value_tgt));
			
			//Verification of equality
			if( sysType_src.equals(sysType_tgt) )
				feasible = false;
			
			if(feasible){
				//Hierarchy verification parents 
				if( !code.getBuilder().getParentClasses().get( sysType_src.getQualifiedName()).isEmpty() ){
					for( TypeDeclaration clase : code.getBuilder().getParentClasses().get( sysType_src.getQualifiedName()) ){
						if( clase.equals(sysType_tgt) ){
							feasible = false;
							break;
						}
							
					}
				}
				
				if(feasible){
					//Hierarchy verification children
					if( !code.getBuilder().getChildClasses().get( sysType_src.getQualifiedName()).isEmpty() ){
						for( TypeDeclaration clase : code.getBuilder().getChildClasses().get( sysType_src.getQualifiedName()) ){
							if( clase.equals(sysType_tgt) ){
								feasible = false;
								break;
							}
						}
					}
				}
			}
			
        }while( !feasible );
		return new OBSERVRefactoring(type.name(),params,feasible);
	}

}
