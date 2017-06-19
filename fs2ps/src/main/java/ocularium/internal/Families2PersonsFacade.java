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

import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IInstanceSpecification;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.presentation.IPresentation;
import com.change_vision.jude.api.inf.project.ProjectAccessor;

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
	 */
	public void transformToPersons() throws ClassNotFoundException, ProjectNotFoundException, InvalidUsingException {
		System.out.println("Transform started...");
		List<IInstanceSpecification> members = getAllMembers();
		for (IInstanceSpecification m : members) {
			System.out.println("fullName= " + m.getSlot("firstName") + " " + getFamilyName(m));
			System.out.println("isFemale= " + isFemale(m));
		}

		System.out.println("Transform ended.");
	}
	/// https://astahblog.com/2013/08/08/uml_object_diagram_in_astah/
	// https://books.google.com.br/books?id=qVZ7DQAAQBAJ&pg=PA251&lpg=PA251&dq=astah+plugin+get+all+instances&source=bl&ots=mPrdw-spVM&sig=MhkjizylZ0S4D8NGZfkfJwfjwUA&hl=pt-BR&sa=X&ved=0ahUKEwii4MHi8qvUAhVLlpAKHQGVADsQ6AEIVDAF#v=onepage&q=astah%20plugin%20get%20all%20instances&f=false
	// Formal Methods: Foundations and Applications: 19th Brazilian Symposium,
	/// SBMF ...
	// edited by Leila Ribeiro, Thierry Lecomte

	private boolean isFemale(IInstanceSpecification m) {
		// TODO Auto-generated method stub
		return false;
	}

	private String getFamilyName(IInstanceSpecification m) {
		// TODO Auto-generated method stub

		return "DummyFamilyName";
	}

	private List<IInstanceSpecification> getAllMembers() throws InvalidUsingException {
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
				}
			}
		}
		return ms;
	}

	// http://members.change-vision.com/javadoc/astah-api/6_5/api/en/doc/astahAPI_reference.html#class_diagram
}
