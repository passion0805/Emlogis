package com.emlogis.model.workflow.dto.details.manager;

import com.emlogis.model.workflow.dto.action.InstanceLog;
import com.emlogis.model.workflow.dto.commentary.RequestCommentary;
import com.emlogis.model.workflow.dto.details.abstracts.AbstractRequestDetailsInfoDto;
import com.emlogis.model.workflow.dto.task.TaskShiftBriefInfoDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by user on 07.05.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailedManagerRequestDetailsDto extends AbstractRequestDetailsInfoDto implements ManagerDetailsInfo {

    @JsonProperty(value = "managerApprovalNeeded", required = true)
    private Boolean managerApprovalNeeded = true;

    @JsonProperty(value = "comment", required = false)
    private String comment;

    @JsonProperty(value = "dateOfAction", required = false)
    private Long dateOfAction;

    @JsonProperty(value = "description", required = true)
    private String description;

    @JsonProperty(value = "commentary", required = true)
    private RequestCommentary commentary;

    @JsonProperty(value = "history", required = true)
    private InstanceLog history;

    @JsonProperty(value = "isRead", required = false)
    private Boolean isRead;

    @JsonProperty(value = "reason", required = false)
    private String reason;

    public DetailedManagerRequestDetailsDto() {
        super();
    }

    public DetailedManagerRequestDetailsDto(
            AbstractRequestDetailsInfoDto prnt, Boolean managerApprovalNeeded, Long dateOfAction, String comment,
            String description, RequestCommentary commentary, InstanceLog history, Boolean isRead, String reason
    ) {
        super(prnt.getRequestId(), prnt.getType(), prnt.getSubmitDate(), prnt.getEventDate(),
                prnt.getExpirationDate(), prnt.getSubmitterShift(), prnt.getSubmitterId(),
                prnt.getSubmitterName(), prnt.getSubmitterTeamName(), prnt.getSubmitterTeamId(),
                prnt.getSubmitterSiteName(), prnt.getSubmitterSiteId(), prnt.getStatus());
        this.managerApprovalNeeded = managerApprovalNeeded;
        this.dateOfAction = dateOfAction;
        this.comment = comment;
        this.history = history;
        this.commentary = commentary;
        this.description = description;
        this.isRead = isRead;
        this.reason = reason;
    }

    public DetailedManagerRequestDetailsDto(
            String requestId, String type, Long submitionDate, Long eventDate, Long expirationDate,
            TaskShiftBriefInfoDto submitterShift, String submitterId, String submitterName, String submitterTeamName,
            String submitterTeamId, String submitterSiteName, String submitterSiteId, String status,
            Boolean managerApprovalNeeded, String comment, Long dateOfAction,
            String description, RequestCommentary commentary, InstanceLog history, Boolean isRead, String reason
    ) {
        super(requestId, type, submitionDate, eventDate, expirationDate, submitterShift, submitterId, submitterName,
                submitterTeamName, submitterTeamId, submitterSiteName, submitterSiteId, status);
        this.managerApprovalNeeded = managerApprovalNeeded;
        this.comment = comment;
        this.dateOfAction = dateOfAction;
        this.history = history;
        this.commentary = commentary;
        this.description = description;
        this.isRead = isRead;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getManagerApprovalNeeded() {
        return managerApprovalNeeded;
    }

    public void setManagerApprovalNeeded(Boolean managerApprovalNeeded) {
        this.managerApprovalNeeded = managerApprovalNeeded;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getDateOfAction() {
        return dateOfAction;
    }

    public void setDateOfAction(Long dateOfAction) {
        this.dateOfAction = dateOfAction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestCommentary getCommentary() {
        return commentary;
    }

    public void setCommentary(RequestCommentary commentary) {
        this.commentary = commentary;
    }

    public InstanceLog getHistory() {
        return history;
    }

    public void setHistory(InstanceLog history) {
        this.history = history;
    }
}
