package com.ctrip.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by huang_xw on 2016/5/3.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
@Entity @javax.persistence.Table(name = "t_module_history", schema = "", catalog = "test")
public class TModuleHistoryEntity implements Serializable {
    private String uuid;

    @Id @javax.persistence.Column(name = "uuid") public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private String id;

    @Basic @javax.persistence.Column(name = "id") public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int scheduleTimes;

    @Id @javax.persistence.Column(name = "schedule_times") public int getScheduleTimes() {
        return scheduleTimes;
    }

    public void setScheduleTimes(int scheduleTimes) {
        this.scheduleTimes = scheduleTimes;
    }

    private String flowUuid;

    @Basic @javax.persistence.Column(name = "flow_uuid") public String getFlowUuid() {
        return flowUuid;
    }

    public void setFlowUuid(String flowUuid) {
        this.flowUuid = flowUuid;
    }

    private String owner;

    @Basic @javax.persistence.Column(name = "owner") public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    private Timestamp startTime;

    @Basic @javax.persistence.Column(name = "start_time") public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    private Timestamp endTime;

    @Basic @javax.persistence.Column(name = "end_time") public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    private String status;

    @Basic @javax.persistence.Column(name = "status") public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private Integer retries;

    @Basic @javax.persistence.Column(name = "retries") public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    private String logDir;

    @Basic @javax.persistence.Column(name = "log_dir") public String getLogDir() {
        return logDir;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    private String worker;

    @Basic @javax.persistence.Column(name = "worker") public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    private String sparkArgs;

    @Basic @javax.persistence.Column(name = "spark_args") public String getSparkArgs() {
        return sparkArgs;
    }

    public void setSparkArgs(String sparkArgs) {
        this.sparkArgs = sparkArgs;
    }

    private String params;

    @Basic @javax.persistence.Column(name = "params") public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TModuleHistoryEntity that = (TModuleHistoryEntity) o;

        if (scheduleTimes != that.scheduleTimes)
            return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (flowUuid != null ? !flowUuid.equals(that.flowUuid) : that.flowUuid != null)
            return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null)
            return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null)
            return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null)
            return false;
        if (retries != null ? !retries.equals(that.retries) : that.retries != null)
            return false;
        if (logDir != null ? !logDir.equals(that.logDir) : that.logDir != null)
            return false;
        if (worker != null ? !worker.equals(that.worker) : that.worker != null)
            return false;
        if (sparkArgs != null ? !sparkArgs.equals(that.sparkArgs) : that.sparkArgs != null)
            return false;
        if (params != null ? !params.equals(that.params) : that.params != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + scheduleTimes;
        result = 31 * result + (flowUuid != null ? flowUuid.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (retries != null ? retries.hashCode() : 0);
        result = 31 * result + (logDir != null ? logDir.hashCode() : 0);
        result = 31 * result + (worker != null ? worker.hashCode() : 0);
        result = 31 * result + (sparkArgs != null ? sparkArgs.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        return result;
    }
}
