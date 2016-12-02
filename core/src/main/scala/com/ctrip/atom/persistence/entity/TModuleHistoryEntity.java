package com.ctrip.atom.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by huang_xw on 2016/4/20.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
@Entity @Table(name = "t_module_history", schema = "", catalog = "test")
@IdClass(TModuleHistoryEntityPK.class) public class TModuleHistoryEntity {
    private String uuid;
    private String id;
    private int scheduleTimes;
    private String flowUuid;
    private String owner;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private String logDir;
    private String worker;
    private String sparkArgs;
    private String params;

    @Id @Column(name = "uuid") public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic @Column(name = "id") public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id @Column(name = "schedule_times") public int getScheduleTimes() {
        return scheduleTimes;
    }

    public void setScheduleTimes(int scheduleTimes) {
        this.scheduleTimes = scheduleTimes;
    }

    @Basic @Column(name = "flow_uuid") public String getFlowUuid() {
        return flowUuid;
    }

    public void setFlowUuid(String flowUuid) {
        this.flowUuid = flowUuid;
    }

    @Basic @Column(name = "owner") public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Basic @Column(name = "start_time") public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic @Column(name = "end_time") public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic @Column(name = "status") public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic @Column(name = "log_dir") public String getLogDir() {
        return logDir;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    @Basic @Column(name = "worker") public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    @Basic @Column(name = "spark_args") public String getSparkArgs() {
        return sparkArgs;
    }

    public void setSparkArgs(String sparkArgs) {
        this.sparkArgs = sparkArgs;
    }

    @Basic @Column(name = "params") public String getParams() {
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
        result = 31 * result + (logDir != null ? logDir.hashCode() : 0);
        result = 31 * result + (worker != null ? worker.hashCode() : 0);
        result = 31 * result + (sparkArgs != null ? sparkArgs.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        return result;
    }
}
