/**************************************************************************
 * copyright file="OofSettings.java" company="Microsoft"
 *     Copyright (c) Microsoft Corporation.  All rights reserved.
 * 
 * Defines the OofSettings.java.
 **************************************************************************/
package microsoft.exchange.webservices.data;

import javax.xml.stream.XMLStreamException;

/**
 * Represents a user's Out of Office (OOF) settings.
 * 
 */
public final class OofSettings extends 
ComplexProperty implements ISelfValidate {

	/** The state. */
	private OofState state = OofState.Disabled;

	/** The external audience. */
	private OofExternalAudience externalAudience = OofExternalAudience.None;

	/** The allow external oof. */
	private OofExternalAudience allowExternalOof = OofExternalAudience.None;

	/** The duration. */
	private TimeWindow duration;

	/** The internal reply. */
	private OofReply internalReply;

	/** The external reply. */
	private OofReply externalReply;

	/**
	 * Serializes an OofReply. Emits an empty OofReply in case the one passed in
	 * is null.
	 * 
	 * @param oofReply
	 *            The oof reply
	 * @param writer
	 *            The writer
	 * @param xmlElementName
	 *            Name of the xml element
	 * @throws javax.xml.stream.XMLStreamException
	 *             the xML stream exception
	 * @throws ServiceXmlSerializationException
	 *             the service xml serialization exception
	 */
	private void serializeOofReply(OofReply oofReply,
			EwsServiceXmlWriter writer, String xmlElementName)
			throws XMLStreamException, ServiceXmlSerializationException {
		if (oofReply != null) {
			oofReply.writeToXml(writer, xmlElementName);
		} else {
			OofReply.writeEmptyReplyToXml(writer, xmlElementName);
		}
	}

	/**
	 * Initializes a new instance of OofSettings.
	 */
	public OofSettings()

	{
		super();
	}

	/**
	 * Tries to read element from XML.
	 * 
	 * @param reader
	 *            The reader
	 * @return True if appropriate element was read.
	 * @throws Exception
	 *             the exception
	 */
	@Override
	protected boolean tryReadElementFromXml(EwsServiceXmlReader reader)
			throws Exception {
		if (reader.getLocalName().equals(XmlElementNames.OofState)) {
			this.state = reader.readValue(OofState.class);
			return true;
		} else if (reader.getLocalName().equals(
				XmlElementNames.ExternalAudience)) {
			this.externalAudience = reader.readValue(OofExternalAudience.class);
			return true;
		} else if (reader.getLocalName().equals(XmlElementNames.Duration)) {
			this.duration = new TimeWindow();
			this.duration.loadFromXml(reader);
			return true;
		} else if (reader.getLocalName().equals(XmlElementNames.InternalReply)) {
			this.internalReply = new OofReply();
			this.internalReply.loadFromXml(reader, reader.getLocalName());
			return true;
		} else if (reader.getLocalName().equals(XmlElementNames.ExternalReply)) {
			this.externalReply = new OofReply();
			this.externalReply.loadFromXml(reader, reader.getLocalName());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Writes elements to XML.
	 * 
	 * @param writer
	 *            The writer
	 * @throws Exception
	 *             the exception
	 */
	@Override
	protected void writeElementsToXml(EwsServiceXmlWriter writer)
			throws Exception {
		super.writeElementsToXml(writer);

		writer.writeElementValue(XmlNamespace.Types, XmlElementNames.OofState,
				this.getState());

		writer.writeElementValue(XmlNamespace.Types,
				XmlElementNames.ExternalAudience, this.getExternalAudience());

		if (this.getDuration() != null && this.getState() == OofState.Scheduled) {
			this.getDuration().writeToXml(writer, XmlElementNames.Duration);
		}

		this.serializeOofReply(this.getInternalReply(), writer,
				XmlElementNames.InternalReply);
		this.serializeOofReply(this.getExternalReply(), writer,
				XmlElementNames.ExternalReply);
	}

	/**
	 * Gets the user's OOF state.
	 * 
	 * @return The user's OOF state.
	 */
	public OofState getState() {
		return state;
	}

	/**
	 * Sets the user's OOF state.
	 * 
	 * @param state
	 *            the new state
	 */
	public void setState(OofState state) {
		this.state = state;
	}

	/**
	 * Gets a value indicating who should receive external OOF messages.
	 * 
	 * @return the external audience
	 */
	public OofExternalAudience getExternalAudience() {
		return externalAudience;
	}

	/**
	 * Sets a value indicating who should receive external OOF messages.
	 * 
	 * @param externalAudience
	 *            the new external audience
	 */
	public void setExternalAudience(OofExternalAudience externalAudience) {
		this.externalAudience = externalAudience;
	}

	/**
	 * Gets the duration of the OOF status when State is set to
	 * OofState.Scheduled.
	 * 
	 * @return the duration
	 */
	public TimeWindow getDuration() {
		return duration;
	}

	/**
	 * Sets the duration of the OOF status when State is set to
	 * OofState.Scheduled.
	 * 
	 * @param duration
	 *            the new duration
	 */
	public void setDuration(TimeWindow duration) {
		this.duration = duration;
	}

	/**
	 * Gets the OOF response sent other users in the user's domain or trusted
	 * domain.
	 * 
	 * @return the internal reply
	 */
	public OofReply getInternalReply() {
		return internalReply;
	}

	/**
	 * Sets the OOF response sent other users in the user's domain or trusted
	 * domain.
	 * 
	 * @param internalReply
	 *            the new internal reply
	 */
	public void setInternalReply(OofReply internalReply) {
		this.internalReply = internalReply;
	}

	/**
	 * Gets the OOF response sent to addresses outside the user's domain or
	 * trusted domain.
	 * 
	 * @return the external reply
	 */
	public OofReply getExternalReply() {
		return externalReply;
	}

	/**
	 * Sets the OOF response sent to addresses outside the user's domain or
	 * trusted domain.
	 * 
	 * @param externalReply
	 *            the new external reply
	 */
	public void setExternalReply(OofReply externalReply) {
		this.externalReply = externalReply;
	}

	/**
	 * Gets a value indicating the authorized external OOF notifications.
	 * 
	 * @return the allow external oof
	 */
	public OofExternalAudience getAllowExternalOof() {
		return allowExternalOof;
	}

	/**
	 * Sets a value indicating the authorized external OOF notifications.
	 * 
	 * @param allowExternalOof
	 *            the new allow external oof
	 */
	public void setAllowExternalOof(OofExternalAudience allowExternalOof) {
		this.allowExternalOof = allowExternalOof;
	}

	/**
	 * Validates this instance.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public void validate() throws Exception {
		if (this.getState() == OofState.Scheduled) {
			if (this.getDuration() == null) {
				throw new ArgumentException(
						Strings.DurationMustBeSpecifiedWhenScheduled);
			}

			EwsUtilities.validateParam(this.getDuration(), "Duration");
		}
	}

}
