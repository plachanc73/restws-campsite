package ca.qc.plachanc73.restws.data.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 7501619931915491669L;

	/**
	 * Returns the id of the entity.
	 *
	 * @return the id
	 */
	public abstract Long getId();

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().getName().split("_")[0].equals(obj.getClass().getName().split("_")[0])) {
			return false;
		}

		AbstractEntity that = (AbstractEntity) obj;

		return this.getId() == null ? false : this.getId().equals(that.getId());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hashCode = 17;

		hashCode += null == getId() ? 0 : getId().hashCode() * 31;

		return hashCode;
	}
}