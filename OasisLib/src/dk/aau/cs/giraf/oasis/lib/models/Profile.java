package dk.aau.cs.giraf.oasis.lib.models;

import java.util.HashMap;

public class Profile {

	private long id;
	private String firstname;
	private String surname;
	private String middlename;
	private long pRole;
	private long phone;
	private String picture;
	private Setting setting;
	private static String _output = "{0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}";

	/**
	 * Constructor with parameters
	 * 
	 * @param firstname the firstname to set
	 * @param surname the surname to set
	 * @param middlename the middlename to set
	 * @param pRole the role to set
	 * @param phone the phone to set
	 * @param picture the picture to set
	 * @param setting the setting to set
	 */
	public Profile(String firstname, String surname, String middlename, long pRole, long phone, String picture, Setting setting) {
		this.firstname = firstname;
		this.surname = surname;
		this.middlename = middlename;
		this.pRole = pRole;
		this.phone = phone;
		this.picture = picture;
		this.setting = setting;
	}

	/**
	 * Empty constructor
	 */
	public Profile() {

	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the name to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the middlename
	 */
	public String getMiddlename() {
		return middlename;
	}
	/**
	 * @param middlename the middlename to set
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	/**
	 * @return the pRole
	 */
	public long getPRole() {
		return pRole;
	}
	/**
	 * @param pRole the role to set
	 */
	public void setPRole(long pRole) {
		this.pRole = pRole;
	}
	/**
	 * @return the phone
	 */
	public long getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(long phone) {
		this.phone = phone;
	}
	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}
	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	/**
	 * @return the idCertificate
	 */
	public Setting getSetting() {
		return setting;
	}
	/**
	 * @param idCertificate the idCertificate to set
	 */
	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	/**
	 * Set output
	 *  {0} = ID
	 *  {1} = Firstname
	 *  {2} = Middlename
	 *  {3} = Surname
	 *  {4} = Role
	 *  {5} = Phone
	 *  {6} = Picture
	 *  {7} = Setting
	 * @param output String to set
	 */
	public static void setOutput(String output) {
		_output = output;
	}

	@Override
	/**
	 * toString for profile
	 */
	public String toString() {
		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getId()));
		localOutput = localOutput.replace("{1}", getFirstname());
		localOutput = localOutput.replace("{2}", getMiddlename());
		localOutput = localOutput.replace("{3}", getSurname());
		localOutput = localOutput.replace("{4}", String.valueOf(getPRole()));
		localOutput = localOutput.replace("{5}", String.valueOf(getPhone()));
		localOutput = localOutput.replace("{6}", getPicture());
		localOutput = localOutput.replace("{7}", getSetting().toString());

		return localOutput;
	}
}
