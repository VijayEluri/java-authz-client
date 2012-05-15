package org.ccci.gto.authorization.object;

import org.ccci.gto.authorization.AuthzObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractObject implements AuthzObject {
    private final static int HASHSEED = 31;
    final private Namespace ns;
    final private String name;

    public AbstractObject(final Namespace ns, final String name) {
	// throw an error if a name is not specified
	if (name == null) {
	    throw new IllegalArgumentException("Authorization objects require a name to be specified");
	}

	// store the namespace and name in this object
	this.ns = ns;
	this.name = name;
    }

    public AbstractObject(final Element object) {
        this(new Namespace(object.getAttributeNS(null, "namespace")), object.getAttributeNS(null, "name"));
    }

    public final Namespace getNamespace() {
        return this.ns;
    }

    public final String getName() {
        return this.name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (this.getClass() != obj.getClass()) {
	    return false;
	}
	final AbstractObject other = (AbstractObject) obj;
	if (this.name == null) {
	    if (other.name != null) {
		return false;
	    }
	} else if (!this.name.equalsIgnoreCase(other.name)) {
	    return false;
	}
	if (this.ns == null) {
	    if (other.ns != null) {
		return false;
	    }
	} else if (!this.ns.equals(other.ns)) {
	    return false;
	}
	return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	int hash = 1;
	hash = HASHSEED * hash + this.name.toLowerCase().hashCode();
	hash = HASHSEED * hash + ((this.ns == null) ? 0 : this.ns.hashCode());
	return hash;
    }

    @Override
    public final String toString() {
	if (this.ns == null || this.ns.getName().length() == 0) {
	    return this.name;
	} else {
	    return this.ns + "|" + this.name;
	}
    }

    public abstract Element toXml(final Document doc);
}
