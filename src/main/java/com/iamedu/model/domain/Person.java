package com.iamedu.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.solr.analysis.PhoneticFilterFactory;
import org.apache.solr.analysis.StandardFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.constraints.Email;

@Entity
@Indexed
@AnalyzerDef(name = "phonetic", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
		@TokenFilterDef(factory = StandardFilterFactory.class),
		@TokenFilterDef(factory = PhoneticFilterFactory.class, params = {
				@Parameter(name = "encoder", value = "DoubleMetaphone"),
				@Parameter(name = "inject", value = "false") }) })
public class Person implements DomainObject {

	private static final long serialVersionUID = 2094131416527243036L;

	private Long id;
	private String name;
	private String slug;
	private String email;
	private Integer version;

	@Id
	@DocumentId
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false)
	@NotNull
	@Field
	public String getName() {
		return name;
	}

	@Field
	public void setName(String name) {
		this.name = name;
	}

	@Fields({
			@Field(analyzer = @Analyzer(impl = SpanishAnalyzer.class)),
			@Field(name = "slug_phonetic", analyzer = @Analyzer(definition = "phonetic")) })
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	@NotNull
	@Email
	@Field
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
