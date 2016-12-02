package com.ctrip.persistence.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 所有单据父类
 *
 * @author jtzhang
 */
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity implements Serializable {
    private Long id;
    private Integer version;
    private Date createdDate;
    private Date lastModified;

    @Id @GeneratedValue(generator = "hib_hilo", strategy = GenerationType.TABLE)
    @GenericGenerator(name = "hib_hilo",
        strategy = "org.hibernate.id.TableHiLoGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(value = "hibernate_id_generation", name = "table")})
    public Long getId() {
        return id;
    }

    public BaseEntity setId(Long id) {
        this.id = id;
        return this;
    }

    @Version @Column(name = "version", nullable = false) public Integer getVersion() {
        return version;
    }

    private void setVersion(Integer version) {
        this.version = version;
    }

    @CreatedDate @Temporal(TemporalType.TIMESTAMP) @Column(name = "created_date", nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @LastModifiedDate() @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified", nullable = false) public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override public String toString() {
        return new ToStringBuilder(this).append("id", id).append("version", version)
            .append("createdDate", createdDate).append("lastModified", lastModified).toString();
    }
}
