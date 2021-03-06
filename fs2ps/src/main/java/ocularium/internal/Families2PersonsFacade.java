/*
 * Copyright (c) 2016, Ocularium. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 * and associated documentation files (the "Software"), to deal in the Software without restriction, 
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, 
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies 
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE 
 * AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 * Please contact Ocularium at http://ocularium.github.io/ocularium if you need additional 
 * information or have any questions.
 */
package ocularium.internal;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.jude.api.inf.editor.BasicModelEditor;
import com.change_vision.jude.api.inf.editor.ClassDiagramEditor;
import com.change_vision.jude.api.inf.editor.ModelEditorFactory;
import com.change_vision.jude.api.inf.editor.TransactionManager;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.IInstanceSpecification;
import com.change_vision.jude.api.inf.model.ILinkEnd;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.presentation.IPresentation;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;

/**
 * Families2Persons Façade.
 * 
 * @author marco.mangan@gmail.com
 *
 */
public class Families2PersonsFacade {

	/**
	 * An Astah project containing UML elements and instances (objects).
	 */
	private IModel project;

	/**
	 * 
	 * plugin actions and views use this façade.
	 * 
	 * @param project
	 *            an Astah project containing UML elements and instances
	 *            (objects).
	 */
	public Families2PersonsFacade(IModel project) {
		super();
		assert project != null;
		this.project = project;
		assert this.project != null;
	}

	/**
	 * 
	 * @param prjAccessor
	 * @return
	 * @throws ProjectNotFoundException
	 */
	public static String getOclProjectPath(ProjectAccessor prjAccessor) throws ProjectNotFoundException {
		assert prjAccessor != null;

		return prjAccessor.getProjectPath() + ".ocl";
	}

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws ProjectNotFoundException
	 * @throws InvalidUsingException
	 * @throws InvalidEditingException 
	 */
	public void transformToPersons() throws ClassNotFoundException, ProjectNotFoundException, InvalidUsingException, InvalidEditingException {
		System.out.println("Transform started...");
		List<IInstanceSpecification> members = getAllMembers();
		for (IInstanceSpecification m : members) {
			System.out.println("fullName= " + getFirstName(m) + " " + getFamilyName(m));
			System.out.println("isFemale= " + isFemale(m));
		}
// http://members.change-vision.com/javadoc/astah-api/6_5/api/en/doc/astahAPI_create.html
		// http://members.change-vision.com/javadoc/astah-api/6_5/api/en/doc/astahAPI_diagram_create.html
	    createTargetDiagram();
        
		System.out.println("Transform ended.");
	}
	/// https://astahblog.com/2013/08/08/uml_object_diagram_in_astah/
	// https://books.google.com.br/books?id=qVZ7DQAAQBAJ&pg=PA251&lpg=PA251&dq=astah+plugin+get+all+instances&source=bl&ots=mPrdw-spVM&sig=MhkjizylZ0S4D8NGZfkfJwfjwUA&hl=pt-BR&sa=X&ved=0ahUKEwii4MHi8qvUAhVLlpAKHQGVADsQ6AEIVDAF#v=onepage&q=astah%20plugin%20get%20all%20instances&f=false
	// Formal Methods: Foundations and Applications: 19th Brazilian Symposium,
	/// SBMF ...
	// edited by Leila Ribeiro, Thierry Lecomte

	private void createTargetDiagram() throws ClassNotFoundException, InvalidUsingException, InvalidEditingException {
		ProjectAccessor projectAccessor =  ProjectAccessorFactory.getProjectAccessor();

	    ClassDiagramEditor cde = projectAccessor.getDiagramEditorFactory().getClassDiagramEditor();

        TransactionManager.beginTransaction();
        IDiagram iClassDiagram = cde.createClassDiagram(project, "Target-model");
        //TODO: add objects to diagram
        //http://members.change-vision.com/javadoc/astah-api/6_5/api/en/doc/astahAPI_presentation_create.html
    
        //http://members.change-vision.com/javadoc/astah-api/6_5/api/en/doc/astahAPI_create.html
        BasicModelEditor basicModelEditor = ModelEditorFactory.getBasicModelEditor();
//basicModelEditor.create
  //      TransactionManager.endTransaction();
        //http://astah.net/faq/community/does-astah-support-object-diagrams
	}

	private String getFirstName(IInstanceSpecification m) {
		return m.getSlot("firstName").getValue();
	}

	private boolean isFemale(IInstanceSpecification m) throws InvalidUsingException {
		ILinkEnd[] les = m.getLinkEnds();
		String relation = les[0].getName();

		// TODO: uncomment these for debug
		//for (ILinkEnd iLinkEnd : les) {
		//	System.out.println("Link: "+iLinkEnd.getName());
		//}
		
		// FIXME: ATL example uses familyMother and familyDaughter
		if (relation.equals("mother"))
			return true;
		if (relation.equals("daughters"))
			return true;		
		
		return false;
	}

	private String getFamilyName(IInstanceSpecification m) throws InvalidUsingException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//m.getAllResidents();
		//m.getClientDependencies();
		//m.getClientRealizations();
		//m.getClientUsages();
		IPresentation[] ps = m.getPresentations();
		for (IPresentation iPresentation : ps) {
			System.out.println("Presentation: "+iPresentation);
			//iPresentation.get''
		}
		//ILinkEnd[] les = m.getLinkEnds();
		// Not containers... IElement[] cs = les[0].getContainers();
		//for (IElement iElement : cs) {
		//	System.out.println(iElement);
		//}		
		// Not container... System.out.println(les[0].getContainer());
	

		return "DummyFamilyName";
	}

	private List<IInstanceSpecification> getAllMembers() throws InvalidUsingException  {
		List<IInstanceSpecification> ms = new ArrayList<IInstanceSpecification>();
		IDiagram[] diagrams = project.getDiagrams();
		for (int i = 0; i < diagrams.length; i++) {
			IPresentation[] iPresentations = diagrams[i].getPresentations();
			for (IPresentation p : iPresentations) {
				if (p.getType().equals("InstanceSpecification")) {
					IInstanceSpecification iInstanceSpecification = (IInstanceSpecification) p.getModel();
					if (iInstanceSpecification.getClassifier().getName().equals("Member")) {
						ms.add(iInstanceSpecification);
					}
				} else {
					System.out.println("Presentation: " + p);
				}
			}
		}
		return ms;
	}

	// http://members.change-vision.com/javadoc/astah-api/6_5/api/en/doc/astahAPI_reference.html#class_diagram
}
