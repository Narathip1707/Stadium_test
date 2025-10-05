<mxfile host="app.diagrams.net">
  <diagram name="Complete Class Diagram - 20 Classes" id="complete-class-diagram">
    <mxGraphModel dx="2000" dy="1200" grid="1" gridSize="10" guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="1" pageScale="1" pageWidth="1800" pageHeight="2400" math="0" shadow="0">
      <root>
        <mxCell id="0" />
        <mxCell id="1" parent="0" />
        
        <!-- ========================================= -->
        <!-- PERSON HIERARCHY -->
        <!-- ========================================= -->
        
        <!-- Person Class (Abstract) -->
        <mxCell id="person" value="&lt;&lt;abstract&gt;&gt;&#xa;Person" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=40;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#dae8fc;strokeColor=#6c8ebf;fontFamily=Helvetica;fontSize=12;" vertex="1" parent="1">
          <mxGeometry x="760" y="40" width="280" height="280" as="geometry" />
        </mxCell>
        <mxCell id="person-attrs" value="- personId : int {PK}&#xa;- firstName : String(100)&#xa;- lastName : String(100)&#xa;- email : String(100) {UK}&#xa;- phone : String(20)&#xa;- address : String(300)&#xa;- createdAt : Timestamp&#xa;- updatedAt : Timestamp" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="person">
          <mxGeometry y="40" width="280" height="120" as="geometry" />
        </mxCell>
        <mxCell id="person-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="person">
          <mxGeometry y="160" width="280" height="8" as="geometry" />
        </mxCell>
        <mxCell id="person-methods" value="+ getFullName() : String&#xa;+ validateEmail() : boolean&#xa;+ validatePhone() : boolean&#xa;+ getRole() : String {abstract}" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;fontStyle=0" vertex="1" parent="person">
          <mxGeometry y="168" width="280" height="112" as="geometry" />
        </mxCell>

        <!-- Account Class -->
        <mxCell id="account" value="Account" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1">
          <mxGeometry x="1120" y="40" width="300" height="220" as="geometry" />
        </mxCell>
        <mxCell id="account-attrs" value="- accountId : int {PK}&#xa;- personId : int {FK}&#xa;- username : String(50) {UK}&#xa;- password : String(255)&#xa;- lastLogin : Timestamp&#xa;- isActive : boolean&#xa;- failedLoginAttempts : int" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="account">
          <mxGeometry y="26" width="300" height="104" as="geometry" />
        </mxCell>
        <mxCell id="account-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="account">
          <mxGeometry y="130" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="account-methods" value="+ authenticate(password) : boolean&#xa;+ changePassword(old, new) : boolean&#xa;+ resetPassword() : String&#xa;+ lockAccount() : void" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="account">
          <mxGeometry y="138" width="300" height="82" as="geometry" />
        </mxCell>

        <!-- Customer Class -->
        <mxCell id="customer" value="Customer" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="400" y="400" width="280" height="240" as="geometry" />
        </mxCell>
        <mxCell id="customer-attrs" value="- customerId : int {PK}&#xa;- personId : int {FK}&#xa;- membershipLevel : String(20)&#xa;- loyaltyPoints : int&#xa;- totalBookings : int&#xa;- registrationDate : Date&#xa;- isVIP : boolean" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="customer">
          <mxGeometry y="26" width="280" height="104" as="geometry" />
        </mxCell>
        <mxCell id="customer-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="customer">
          <mxGeometry y="130" width="280" height="8" as="geometry" />
        </mxCell>
        <mxCell id="customer-methods" value="+ makeBooking() : Booking&#xa;+ cancelBooking(id) : boolean&#xa;+ viewBookingHistory() : List&#xa;+ addLoyaltyPoints(pts) : void&#xa;+ upgradeMembership() : boolean" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="customer">
          <mxGeometry y="138" width="280" height="102" as="geometry" />
        </mxCell>

        <!-- Employee Class (Abstract) -->
        <mxCell id="employee" value="&lt;&lt;abstract&gt;&gt;&#xa;Employee" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=40;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1">
          <mxGeometry x="1120" y="400" width="280" height="240" as="geometry" />
        </mxCell>
        <mxCell id="employee-attrs" value="- employeeId : int {PK}&#xa;- personId : int {FK}&#xa;- employeeCode : String(15) {UK}&#xa;- department : String(50)&#xa;- position : String(50)&#xa;- salary : Decimal(10,2)&#xa;- hireDate : Date" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="employee">
          <mxGeometry y="40" width="280" height="104" as="geometry" />
        </mxCell>
        <mxCell id="employee-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="employee">
          <mxGeometry y="144" width="280" height="8" as="geometry" />
        </mxCell>
        <mxCell id="employee-methods" value="+ clockIn() : boolean&#xa;+ clockOut() : boolean&#xa;+ performDuty() : void {abstract}&#xa;+ getRole() : String" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="employee">
          <mxGeometry y="152" width="280" height="88" as="geometry" />
        </mxCell>

        <!-- BookingStaff Class -->
        <mxCell id="bookingstaff" value="BookingStaff" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="600" y="720" width="320" height="220" as="geometry" />
        </mxCell>
        <mxCell id="bookingstaff-attrs" value="- staffId : int {PK}&#xa;- employeeId : int {FK}&#xa;- shiftSchedule : String(50)&#xa;- bookingQuota : int&#xa;- performanceRating : Decimal(3,2)" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="bookingstaff">
          <mxGeometry y="26" width="320" height="84" as="geometry" />
        </mxCell>
        <mxCell id="bookingstaff-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="bookingstaff">
          <mxGeometry y="110" width="320" height="8" as="geometry" />
        </mxCell>
        <mxCell id="bookingstaff-methods" value="+ approveBooking(id) : boolean&#xa;+ rejectBooking(id, reason) : boolean&#xa;+ searchBookings(criteria) : List&#xa;+ updateBookingStatus(id, status) : boolean&#xa;+ assignField(bookingId, fieldId) : boolean" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="bookingstaff">
          <mxGeometry y="118" width="320" height="102" as="geometry" />
        </mxCell>

        <!-- SystemAdmin Class -->
        <mxCell id="systemadmin" value="SystemAdmin" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="960" y="720" width="280" height="220" as="geometry" />
        </mxCell>
        <mxCell id="systemadmin-attrs" value="- adminId : int {PK}&#xa;- employeeId : int {FK}&#xa;- accessLevel : int&#xa;- permissions : Text(500)&#xa;- lastBackup : Timestamp" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="systemadmin">
          <mxGeometry y="26" width="280" height="84" as="geometry" />
        </mxCell>
        <mxCell id="systemadmin-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="systemadmin">
          <mxGeometry y="110" width="280" height="8" as="geometry" />
        </mxCell>
        <mxCell id="systemadmin-methods" value="+ manageUsers() : void&#xa;+ manageFields() : void&#xa;+ systemMaintenance() : boolean&#xa;+ backupDatabase() : boolean&#xa;+ viewAuditLogs() : List" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="systemadmin">
          <mxGeometry y="118" width="280" height="102" as="geometry" />
        </mxCell>

        <!-- AccountingStaff Class -->
        <mxCell id="accountingstaff" value="AccountingStaff" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="1280" y="720" width="300" height="220" as="geometry" />
        </mxCell>
        <mxCell id="accountingstaff-attrs" value="- accountingId : int {PK}&#xa;- employeeId : int {FK}&#xa;- financeAuthority : String(50)&#xa;- certification : String(100)&#xa;- approvalLimit : Decimal(12,2)" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="accountingstaff">
          <mxGeometry y="26" width="300" height="84" as="geometry" />
        </mxCell>
        <mxCell id="accountingstaff-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="accountingstaff">
          <mxGeometry y="110" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="accountingstaff-methods" value="+ generateInvoice(paymentId) : Invoice&#xa;+ processRefund(paymentId, amt) : boolean&#xa;+ reconcileTransactions() : Report&#xa;+ auditFinancialRecords() : Report&#xa;+ verifyPayment(paymentId) : boolean" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="accountingstaff">
          <mxGeometry y="118" width="300" height="102" as="geometry" />
        </mxCell>

        <!-- ManagingDirector Class -->
        <mxCell id="managingdirector" value="ManagingDirector" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1">
          <mxGeometry x="960" y="1000" width="300" height="200" as="geometry" />
        </mxCell>
        <mxCell id="managingdirector-attrs" value="- directorId : int {PK}&#xa;- employeeId : int {FK}&#xa;- authorityLevel : int&#xa;- signatureLimit : Decimal(15,2)" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="managingdirector">
          <mxGeometry y="26" width="300" height="64" as="geometry" />
        </mxCell>
        <mxCell id="managingdirector-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="managingdirector">
          <mxGeometry y="90" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="managingdirector-methods" value="+ viewExecutiveDashboard() : Dashboard&#xa;+ viewReports(type, period) : Report&#xa;+ analyzeBusinessPerformance() : Analytics&#xa;+ approveDecisions(requestId) : boolean&#xa;+ setBusinessPolicies() : void" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="managingdirector">
          <mxGeometry y="98" width="300" height="102" as="geometry" />
        </mxCell>

        <!-- MarketingStaff Class -->
        <mxCell id="marketingstaff" value="MarketingStaff" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1">
          <mxGeometry x="1320" y="1000" width="320" height="200" as="geometry" />
        </mxCell>
        <mxCell id="marketingstaff-attrs" value="- marketingId : int {PK}&#xa;- employeeId : int {FK}&#xa;- campaignAccess : String(100)&#xa;- targetRegion : String(50)&#xa;- activeCampaigns : int" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="marketingstaff">
          <mxGeometry y="26" width="320" height="74" as="geometry" />
        </mxCell>
        <mxCell id="marketingstaff-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="marketingstaff">
          <mxGeometry y="100" width="320" height="8" as="geometry" />
        </mxCell>
        <mxCell id="marketingstaff-methods" value="+ createCampaign(campaign) : boolean&#xa;+ analyzeTrends() : Analytics&#xa;+ generateSalesReport() : Report&#xa;+ trackCampaignPerformance(id) : Analytics&#xa;+ segmentCustomers() : List" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="marketingstaff">
          <mxGeometry y="108" width="320" height="92" as="geometry" />
        </mxCell>
                <!-- ========================================= -->
        <!-- BUSINESS ENTITIES -->
        <!-- ========================================= -->

        <!-- Field Class -->
        <mxCell id="field" value="Field" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="40" y="1280" width="300" height="260" as="geometry" />
        </mxCell>
        <mxCell id="field-attrs" value="- fieldId : int {PK}&#xa;- fieldType : String(50)&#xa;- name : String(100) {UK}&#xa;- description : Text(2000)&#xa;- hourlyRate : Decimal(8,2)&#xa;- location : String(200)&#xa;- status : String(20)&#xa;- capacity : int&#xa;- operatingHours : String(50)" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="field">
          <mxGeometry y="26" width="300" height="134" as="geometry" />
        </mxCell>
        <mxCell id="field-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="field">
          <mxGeometry y="160" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="field-methods" value="+ isAvailable() : boolean&#xa;+ checkAvailability(date, time) : boolean&#xa;+ calculateRate(hours) : Decimal&#xa;+ updateStatus(status) : void&#xa;+ scheduleMaintenance(date) : void" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="field">
          <mxGeometry y="168" width="300" height="92" as="geometry" />
        </mxCell>

        <!-- Service Class -->
        <mxCell id="service" value="Service" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="40" y="1600" width="300" height="200" as="geometry" />
        </mxCell>
        <mxCell id="service-attrs" value="- serviceId : int {PK}&#xa;- fieldId : int {FK}&#xa;- serviceName : String(100)&#xa;- servicePrice : Decimal(8,2)&#xa;- serviceDuration : int&#xa;- availability : boolean&#xa;- serviceCode : String(15) {UK}" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="service">
          <mxGeometry y="26" width="300" height="104" as="geometry" />
        </mxCell>
        <mxCell id="service-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="service">
          <mxGeometry y="130" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="service-methods" value="+ getServiceInfo() : ServiceInfo&#xa;+ updatePrice(newPrice) : void&#xa;+ checkAvailability() : boolean&#xa;+ applyDiscount(discount) : Decimal" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="service">
          <mxGeometry y="138" width="300" height="62" as="geometry" />
        </mxCell>

        <!-- Booking Class -->
        <mxCell id="booking" value="Booking" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="400" y="1280" width="300" height="280" as="geometry" />
        </mxCell>
        <mxCell id="booking-attrs" value="- bookingId : int {PK}&#xa;- customerId : int {FK}&#xa;- fieldId : int {FK}&#xa;- staffId : int {FK}&#xa;- bookingRef : String(20) {UK}&#xa;- bookingDate : Date&#xa;- startTime : Time&#xa;- endTime : Time&#xa;- status : String(20)&#xa;- totalAmount : Decimal(10,2)&#xa;- paymentStatus : String(20)" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="booking">
          <mxGeometry y="26" width="300" height="164" as="geometry" />
        </mxCell>
        <mxCell id="booking-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="booking">
          <mxGeometry y="190" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="booking-methods" value="+ calculateTotalAmount() : Decimal&#xa;+ validateBookingTime() : boolean&#xa;+ confirm() : boolean&#xa;+ cancel() : boolean&#xa;+ generateBookingRef() : String" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="booking">
          <mxGeometry y="198" width="300" height="82" as="geometry" />
        </mxCell>

        <!-- BookingDetail Class -->
        <mxCell id="bookingdetail" value="BookingDetail" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="400" y="1620" width="300" height="180" as="geometry" />
        </mxCell>
        <mxCell id="bookingdetail-attrs" value="- detailId : int {PK}&#xa;- bookingId : int {FK}&#xa;- serviceId : int {FK}&#xa;- quantity : int&#xa;- unitPrice : Decimal(8,2)&#xa;- subtotal : Decimal(10,2)&#xa;- discount : Decimal(5,2)" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="bookingdetail">
          <mxGeometry y="26" width="300" height="104" as="geometry" />
        </mxCell>
        <mxCell id="bookingdetail-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="bookingdetail">
          <mxGeometry y="130" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="bookingdetail-methods" value="+ calculateSubtotal() : Decimal&#xa;+ validateQuantity() : boolean&#xa;+ applyDiscount(discount) : void" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="bookingdetail">
          <mxGeometry y="138" width="300" height="42" as="geometry" />
        </mxCell>

        <!-- Payment Class -->
        <mxCell id="payment" value="Payment" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#ffe6cc;strokeColor=#d79b00;" vertex="1" parent="1">
          <mxGeometry x="760" y="1280" width="300" height="260" as="geometry" />
        </mxCell>
        <mxCell id="payment-attrs" value="- paymentId : int {PK}&#xa;- bookingId : int {FK}&#xa;- gatewayId : int {FK}&#xa;- transactionId : String(100) {UK}&#xa;- paymentMethod : String(30)&#xa;- amount : Decimal(10,2)&#xa;- paymentStatus : String(20)&#xa;- paymentDate : Timestamp&#xa;- receiptNumber : String(25) {UK}" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="payment">
          <mxGeometry y="26" width="300" height="134" as="geometry" />
        </mxCell>
        <mxCell id="payment-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="payment">
          <mxGeometry y="160" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="payment-methods" value="+ processPayment() : boolean&#xa;+ verifyPayment() : boolean&#xa;+ refund(amount) : boolean&#xa;+ generateReceipt() : Receipt&#xa;+ generateTransactionId() : String" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="payment">
          <mxGeometry y="168" width="300" height="92" as="geometry" />
        </mxCell>

        <!-- PaymentGateway Class -->
        <mxCell id="paymentgateway" value="PaymentGateway" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#ffe6cc;strokeColor=#d79b00;" vertex="1" parent="1">
          <mxGeometry x="1120" y="1280" width="320" height="240" as="geometry" />
        </mxCell>
        <mxCell id="paymentgateway-attrs" value="- gatewayId : int {PK}&#xa;- gatewayName : String(50)&#xa;- gatewayCode : String(10) {UK}&#xa;- apiEndpoint : String(200)&#xa;- apiKey : String(100)&#xa;- isActive : boolean&#xa;- commissionRate : Decimal(5,4)&#xa;- timeout : int" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="paymentgateway">
          <mxGeometry y="26" width="320" height="124" as="geometry" />
        </mxCell>
        <mxCell id="paymentgateway-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="paymentgateway">
          <mxGeometry y="150" width="320" height="8" as="geometry" />
        </mxCell>
        <mxCell id="paymentgateway-methods" value="+ initializePayment(payment) : String&#xa;+ confirmPayment(txnId) : Result&#xa;+ processRefund(txnId, amt) : Result&#xa;+ validateTransaction(txnId) : boolean" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="paymentgateway">
          <mxGeometry y="158" width="320" height="82" as="geometry" />
        </mxCell>

        <!-- Invoice Class -->
        <mxCell id="invoice" value="Invoice" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#ffe6cc;strokeColor=#d79b00;" vertex="1" parent="1">
          <mxGeometry x="760" y="1600" width="300" height="240" as="geometry" />
        </mxCell>
        <mxCell id="invoice-attrs" value="- invoiceId : int {PK}&#xa;- paymentId : int {FK}&#xa;- accountingId : int {FK}&#xa;- invoiceNumber : String(20) {UK}&#xa;- issueDate : Date&#xa;- dueDate : Date&#xa;- totalAmount : Decimal(10,2)&#xa;- taxAmount : Decimal(8,2)&#xa;- invoiceStatus : String(20)" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="invoice">
          <mxGeometry y="26" width="300" height="134" as="geometry" />
        </mxCell>
        <mxCell id="invoice-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="invoice">
          <mxGeometry y="160" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="invoice-methods" value="+ generateInvoiceNumber() : String&#xa;+ calculateTax(rate) : Decimal&#xa;+ sendInvoice(email) : boolean&#xa;+ markAsPaid() : void" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="invoice">
          <mxGeometry y="168" width="300" height="72" as="geometry" />
        </mxCell>
                <!-- ========================================= -->
        <!-- EXTERNAL SYSTEMS & REPORTING -->
        <!-- ========================================= -->

        <!-- Logistic Class -->
        <mxCell id="logistic" value="Logistic" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="1120" y="1600" width="300" height="220" as="geometry" />
        </mxCell>
        <mxCell id="logistic-attrs" value="- logisticId : int {PK}&#xa;- bookingId : int {FK}&#xa;- trackingNumber : String(25) {UK}&#xa;- deliveryAddress : String(300)&#xa;- deliveryStatus : String(20)&#xa;- courierInfo : String(100)&#xa;- estimatedDelivery : Timestamp&#xa;- actualDelivery : Timestamp" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="logistic">
          <mxGeometry y="26" width="300" height="114" as="geometry" />
        </mxCell>
        <mxCell id="logistic-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="logistic">
          <mxGeometry y="140" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="logistic-methods" value="+ generateTrackingNumber() : String&#xa;+ updateStatus(status) : void&#xa;+ trackDelivery() : TrackingInfo&#xa;+ confirmDelivery(signature) : boolean&#xa;+ sendNotification() : void" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="logistic">
          <mxGeometry y="148" width="300" height="72" as="geometry" />
        </mxCell>

        <!-- Report Class -->
        <mxCell id="report" value="Report" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1">
          <mxGeometry x="1480" y="1280" width="300" height="220" as="geometry" />
        </mxCell>
        <mxCell id="report-attrs" value="- reportId : int {PK}&#xa;- reportType : String(50)&#xa;- reportName : String(100)&#xa;- generatedBy : int {FK}&#xa;- reportPeriod : String(50)&#xa;- startDate : Date&#xa;- endDate : Date&#xa;- filePath : String(500)" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="report">
          <mxGeometry y="26" width="300" height="114" as="geometry" />
        </mxCell>
        <mxCell id="report-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="report">
          <mxGeometry y="140" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="report-methods" value="+ generateReport() : boolean&#xa;+ exportToExcel() : File&#xa;+ exportToPDF() : File&#xa;+ sendByEmail(email) : boolean" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="report">
          <mxGeometry y="148" width="300" height="72" as="geometry" />
        </mxCell>

        <!-- Campaign Class -->
        <mxCell id="campaign" value="Campaign" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1">
          <mxGeometry x="1480" y="1560" width="300" height="240" as="geometry" />
        </mxCell>
        <mxCell id="campaign-attrs" value="- campaignId : int {PK}&#xa;- marketingId : int {FK}&#xa;- campaignName : String(100)&#xa;- campaignType : String(50)&#xa;- budget : Decimal(10,2)&#xa;- startDate : Date&#xa;- endDate : Date&#xa;- status : String(20)&#xa;- impressions : int&#xa;- conversions : int" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="campaign">
          <mxGeometry y="26" width="300" height="144" as="geometry" />
        </mxCell>
        <mxCell id="campaign-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="campaign">
          <mxGeometry y="170" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="campaign-methods" value="+ launch() : boolean&#xa;+ pause() : boolean&#xa;+ trackPerformance() : Analytics&#xa;+ calculateROI() : Decimal" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="campaign">
          <mxGeometry y="178" width="300" height="62" as="geometry" />
        </mxCell>

        <!-- AuditLog Class -->
        <mxCell id="auditlog" value="AuditLog" style="swimlane;fontStyle=1;align=center;verticalAlign=top;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;resizeParentMax=0;resizeLast=0;collapsible=1;marginBottom=0;fillColor=#f5f5f5;strokeColor=#666666;" vertex="1" parent="1">
          <mxGeometry x="1480" y="1860" width="300" height="200" as="geometry" />
        </mxCell>
        <mxCell id="auditlog-attrs" value="- logId : int {PK}&#xa;- userId : int {FK}&#xa;- action : String(100)&#xa;- tableName : String(50)&#xa;- recordId : int&#xa;- oldValue : Text(2000)&#xa;- newValue : Text(2000)&#xa;- timestamp : Timestamp" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="auditlog">
          <mxGeometry y="26" width="300" height="114" as="geometry" />
        </mxCell>
        <mxCell id="auditlog-line" value="" style="line;strokeWidth=1;fillColor=none;align=left;verticalAlign=middle;spacingTop=-1;spacingLeft=3;spacingRight=3;rotatable=0;labelPosition=right;points=[];portConstraint=eastwest;" vertex="1" parent="auditlog">
          <mxGeometry y="140" width="300" height="8" as="geometry" />
        </mxCell>
        <mxCell id="auditlog-methods" value="+ logAction(action, data) : void&#xa;+ getHistory(recordId) : List&#xa;+ searchLogs(criteria) : List" style="text;strokeColor=none;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;rotatable=0;points=[[0,0.5],[1,0.5]];portConstraint=eastwest;" vertex="1" parent="auditlog">
          <mxGeometry y="148" width="300" height="52" as="geometry" />
        </mxCell>

        <!-- ========================================= -->
        <!-- INHERITANCE RELATIONSHIPS -->
        <!-- ========================================= -->

        <!-- Person to Customer -->
        <mxCell id="inherit-person-customer" value="" style="endArrow=block;endSize=16;endFill=0;html=1;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.25;entryY=1;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="customer" target="person">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="540" y="380" as="sourcePoint" />
            <mxPoint x="830" y="330" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        
        <!-- Person to Employee -->
        <mxCell id="inherit-person-employee" value="" style="endArrow=block;endSize=16;endFill=0;html=1;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.75;entryY=1;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="employee" target="person">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="1260" y="380" as="sourcePoint" />
            <mxPoint x="970" y="330" as="targetPoint" />
          </mxGeometry>
        </mxCell>

        <!-- Employee to BookingStaff -->
        <mxCell id="inherit-employee-bookingstaff" value="" style="endArrow=block;endSize=16;endFill=0;html=1;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.2;entryY=1;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="bookingstaff" target="employee">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="760" y="700" as="sourcePoint" />
            <mxPoint x="1176" y="650" as="targetPoint" />
          </mxGeometry>
        </mxCell>

        <!-- Employee to SystemAdmin -->
        <mxCell id="inherit-employee-systemadmin" value="" style="endArrow=block;endSize=16;endFill=0;html=1;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="systemadmin" target="employee">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="1100" y="700" as="sourcePoint" />
            <mxPoint x="1260" y="650" as="targetPoint" />
          </mxGeometry>
        </mxCell>

        <!-- Employee to AccountingStaff -->
        <mxCell id="inherit-employee-accountingstaff" value="" style="endArrow=block;endSize=16;endFill=0;html=1;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.8;entryY=1;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="accountingstaff" target="employee">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="1430" y="700" as="sourcePoint" />
            <mxPoint x="1344" y="650" as="targetPoint" />
          </mxGeometry>
        </mxCell>

        <!-- Employee to ManagingDirector -->
        <mxCell id="inherit-employee-managingdirector" value="" style="endArrow=block;endSize=16;endFill=0;html=1;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;strokeWidth=2;dashed=1;" edge="1" parent="1" source="managingdirector" target="employee">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="1110" y="980" as="sourcePoint" />
            <mxPoint x="1260" y="660" as="targetPoint" />
            <Array as="points">
              <mxPoint x="1110" y="880" />
              <mxPoint x="1260" y="880" />
            </Array>
          </mxGeometry>
        </mxCell>

        <!-- Employee to MarketingStaff -->
        <mxCell id="inherit-employee-marketingstaff" value="" style="endArrow=block;endSize=16;endFill=0;html=1;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.9;entryY=1;entryDx=0;entryDy=0;strokeWidth=2;dashed=1;" edge="1" parent="1" source="marketingstaff" target="employee">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="1480" y="980" as="sourcePoint" />
            <mxPoint x="1372" y="650" as="targetPoint" />
            <Array as="points">
              <mxPoint x="1480" y="880" />
              <mxPoint x="1372" y="880" />
            </Array>
          </mxGeometry>
        </mxCell>

        <!-- ========================================= -->
        <!-- ASSOCIATION RELATIONSHIPS -->
        <!-- ========================================= -->

        <!-- Person to Account (1-1) -->
        <mxCell id="assoc-person-account" value="has" style="endArrow=diamondThin;endFill=1;endSize=24;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="person" target="account">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="1050" y="180" as="sourcePoint" />
            <mxPoint x="1110" y="150" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-person-account-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-person-account">
          <mxGeometry x="-0.75" y="2" relative="1" as="geometry">
            <mxPoint x="-8" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-person-account-mult-2" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-person-account">
          <mxGeometry x="0.75" y="1" relative="1" as="geometry">
            <mxPoint x="8" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- Customer to Booking (1-many) -->
        <mxCell id="assoc-customer-booking" value="makes" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=0.5;exitY=1;exitDx=0;exitDy=0;entryX=0.25;entryY=0;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="customer" target="booking">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="540" y="660" as="sourcePoint" />
            <mxPoint x="475" y="1260" as="targetPoint" />
            <Array as="points">
              <mxPoint x="540" y="1100" />
              <mxPoint x="475" y="1100" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-customer-booking-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-customer-booking">
          <mxGeometry x="-0.85" y="2" relative="1" as="geometry">
            <mxPoint x="-15" y="10" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-customer-booking-mult-2" value="0..*" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-customer-booking">
          <mxGeometry x="0.85" y="1" relative="1" as="geometry">
            <mxPoint x="15" y="-10" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- BookingStaff to Booking (1-many) -->
        <mxCell id="assoc-bookingstaff-booking" value="manages" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=0;exitY=0.5;exitDx=0;exitDy=0;entryX=0.75;entryY=0;entryDx=0;entryDy=0;strokeWidth=2;dashed=1;" edge="1" parent="1" source="bookingstaff" target="booking">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="590" y="830" as="sourcePoint" />
            <mxPoint x="625" y="1260" as="targetPoint" />
            <Array as="points">
              <mxPoint x="520" y="830" />
              <mxPoint x="520" y="1100" />
              <mxPoint x="625" y="1100" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-bookingstaff-booking-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-bookingstaff-booking">
          <mxGeometry x="-0.85" y="2" relative="1" as="geometry">
            <mxPoint x="15" y="10" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-bookingstaff-booking-mult-2" value="0..*" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-bookingstaff-booking">
          <mxGeometry x="0.85" y="1" relative="1" as="geometry">
            <mxPoint x="-15" y="-10" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- Field to Booking (1-many) -->
        <mxCell id="assoc-field-booking" value="hosts" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="field" target="booking">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="350" y="1410" as="sourcePoint" />
            <mxPoint x="390" y="1410" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-field-booking-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-field-booking">
          <mxGeometry x="-0.7" y="2" relative="1" as="geometry">
            <mxPoint x="-8" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-field-booking-mult-2" value="0..*" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-field-booking">
          <mxGeometry x="0.7" y="1" relative="1" as="geometry">
            <mxPoint x="8" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- Field to Service (1-many) -->
        <mxCell id="assoc-field-service" value="provides" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=0.5;exitY=1;exitDx=0;exitDy=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="field" target="service">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="190" y="1560" as="sourcePoint" />
            <mxPoint x="190" y="1590" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-field-service-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-field-service">
          <mxGeometry x="-0.6" y="2" relative="1" as="geometry">
            <mxPoint x="-15" y="5" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-field-service-mult-2" value="0..*" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-field-service">
          <mxGeometry x="0.6" y="1" relative="1" as="geometry">
            <mxPoint x="15" y="-5" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- Booking to BookingDetail (1-many) -->
        <mxCell id="assoc-booking-bookingdetail" value="contains" style="endArrow=diamondThin;endFill=1;endSize=24;html=1;exitX=0.5;exitY=1;exitDx=0;exitDy=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="booking" target="bookingdetail">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="550" y="1580" as="sourcePoint" />
            <mxPoint x="550" y="1610" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-booking-bookingdetail-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-booking-bookingdetail">
          <mxGeometry x="-0.6" y="2" relative="1" as="geometry">
            <mxPoint x="-15" y="5" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-booking-bookingdetail-mult-2" value="0..*" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-booking-bookingdetail">
          <mxGeometry x="0.6" y="1" relative="1" as="geometry">
            <mxPoint x="15" y="-5" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- Service to BookingDetail (1-many) -->
        <mxCell id="assoc-service-bookingdetail" value="included_in" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;strokeWidth=2;dashed=1;" edge="1" parent="1" source="service" target="bookingdetail">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="350" y="1700" as="sourcePoint" />
            <mxPoint x="390" y="1700" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-service-bookingdetail-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-service-bookingdetail">
          <mxGeometry x="-0.7" y="2" relative="1" as="geometry">
            <mxPoint x="-8" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-service-bookingdetail-mult-2" value="0..*" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-service-bookingdetail">
          <mxGeometry x="0.7" y="1" relative="1" as="geometry">
            <mxPoint x="8" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- Booking to Payment (1-1) -->
        <mxCell id="assoc-booking-payment" value="requires" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="booking" target="payment">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="710" y="1420" as="sourcePoint" />
            <mxPoint x="750" y="1420" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-booking-payment-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-booking-payment">
          <mxGeometry x="-0.7" y="2" relative="1" as="geometry">
            <mxPoint x="-8" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-booking-payment-mult-2" value="0..1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-booking-payment">
          <mxGeometry x="0.7" y="1" relative="1" as="geometry">
            <mxPoint x="8" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- Payment to PaymentGateway (many-1) -->
        <mxCell id="assoc-payment-paymentgateway" value="uses" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="payment" target="paymentgateway">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="1070" y="1410" as="sourcePoint" />
            <mxPoint x="1110" y="1410" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-payment-paymentgateway-mult-1" value="0..*" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-payment-paymentgateway">
          <mxGeometry x="-0.7" y="2" relative="1" as="geometry">
            <mxPoint x="-8" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-payment-paymentgateway-mult-2" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-payment-paymentgateway">
          <mxGeometry x="0.7" y="1" relative="1" as="geometry">
            <mxPoint x="8" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- Payment to Invoice (1-1) -->
        <mxCell id="assoc-payment-invoice" value="generates" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=0.5;exitY=1;exitDx=0;exitDy=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="payment" target="invoice">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="910" y="1560" as="sourcePoint" />
            <mxPoint x="910" y="1590" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-payment-invoice-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-payment-invoice">
          <mxGeometry x="-0.6" y="2" relative="1" as="geometry">
            <mxPoint x="-15" y="5" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-payment-invoice-mult-2" value="0..1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-payment-invoice">
          <mxGeometry x="0.6" y="1" relative="1" as="geometry">
            <mxPoint x="15" y="-5" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- AccountingStaff to Invoice (1-many) -->
        <mxCell id="assoc-accountingstaff-invoice" value="creates" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=0.25;exitY=1;exitDx=0;exitDy=0;entryX=1;entryY=0.25;entryDx=0;entryDy=0;strokeWidth=2;dashed=1;" edge="1" parent="1" source="accountingstaff" target="invoice">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="1355" y="960" as="sourcePoint" />
            <mxPoint x="1080" y="1650" as="targetPoint" />
            <Array as="points">
              <mxPoint x="1355" y="1200" />
              <mxPoint x="1200" y="1200" />
              <mxPoint x="1200" y="1660" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-accountingstaff-invoice-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-accountingstaff-invoice">
          <mxGeometry x="-0.85" y="2" relative="1" as="geometry">
            <mxPoint x="15" y="10" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-accountingstaff-invoice-mult-2" value="0..*" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-accountingstaff-invoice">
          <mxGeometry x="0.85" y="1" relative="1" as="geometry">
            <mxPoint x="-15" y="-10" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- Booking to Logistic (1-1 optional) -->
        <mxCell id="assoc-booking-logistic" value="ships" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=1;exitY=1;exitDx=0;exitDy=0;entryX=0;entryY=0.25;entryDx=0;entryDy=0;strokeWidth=2;dashed=1;" edge="1" parent="1" source="booking" target="logistic">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="710" y="1570" as="sourcePoint" />
            <mxPoint x="1110" y="1670" as="targetPoint" />
            <Array as="points">
              <mxPoint x="800" y="1560" />
              <mxPoint x="800" y="1655" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-booking-logistic-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-booking-logistic">
          <mxGeometry x="-0.8" y="2" relative="1" as="geometry">
            <mxPoint x="-10" y="10" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-booking-logistic-mult-2" value="0..1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-booking-logistic">
          <mxGeometry x="0.8" y="1" relative="1" as="geometry">
            <mxPoint x="10" y="-10" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- Employee to Report (1-many) -->
        <mxCell id="assoc-employee-report" value="generates" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=1;exitY=0.75;exitDx=0;exitDy=0;entryX=0;entryY=0.25;entryDx=0;entryDy=0;strokeWidth=2;dashed=1;" edge="1" parent="1" source="employee" target="report">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="1410" y="580" as="sourcePoint" />
            <mxPoint x="1470" y="1335" as="targetPoint" />
            <Array as="points">
              <mxPoint x="1450" y="580" />
              <mxPoint x="1450" y="1335" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-employee-report-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-employee-report">
          <mxGeometry x="-0.85" y="2" relative="1" as="geometry">
            <mxPoint x="15" y="10" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-employee-report-mult-2" value="0..*" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-employee-report">
          <mxGeometry x="0.85" y="1" relative="1" as="geometry">
            <mxPoint x="-15" y="-10" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- MarketingStaff to Campaign (1-many) -->
        <mxCell id="assoc-marketingstaff-campaign" value="manages" style="endArrow=open;endFill=1;endSize=12;html=1;exitX=0.5;exitY=1;exitDx=0;exitDy=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;strokeWidth=2;" edge="1" parent="1" source="marketingstaff" target="campaign">
          <mxGeometry width="160" relative="1" as="geometry">
            <mxPoint x="1480" y="1220" as="sourcePoint" />
            <mxPoint x="1480" y="1540" as="targetPoint" />
            <Array as="points">
              <mxPoint x="1480" y="1380" />
              <mxPoint x="1630" y="1380" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-marketingstaff-campaign-mult-1" value="1" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-marketingstaff-campaign">
          <mxGeometry x="-0.7" y="2" relative="1" as="geometry">
            <mxPoint x="-15" y="10" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="assoc-marketingstaff-campaign-mult-2" value="0..*" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=14;fontStyle=1" vertex="1" connectable="0" parent="assoc-marketingstaff-campaign">
          <mxGeometry x="0.7" y="1" relative="1" as="geometry">
            <mxPoint x="15" y="-10" as="offset" />
          </mxGeometry>
        </mxCell>

        <!-- ========================================= -->
        <!-- LEGEND -->
        <!-- ========================================= -->

        <mxCell id="legend-box" value="" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#f5f5f5;strokeColor=#666666;opacity=80;" vertex="1" parent="1">
          <mxGeometry x="40" y="40" width="300" height="280" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-title" value="LEGEND" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;fontStyle=1;fontSize=18;" vertex="1" parent="1">
          <mxGeometry x="90" y="50" width="200" height="30" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-pk" value="{PK} = Primary Key" style="text;html=1;strokeColor=none;fillColor=none;align=left;verticalAlign=middle;whiteSpace=wrap;rounded=0;fontSize=12;" vertex="1" parent="1">
          <mxGeometry x="60" y="90" width="260" height="20" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-fk" value="{FK} = Foreign Key" style="text;html=1;strokeColor=none;fillColor=none;align=left;verticalAlign=middle;whiteSpace=wrap;rounded=0;fontSize=12;" vertex="1" parent="1">
          <mxGeometry x="60" y="115" width="260" height="20" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-uk" value="{UK} = Unique Key (Candidate)" style="text;html=1;strokeColor=none;fillColor=none;align=left;verticalAlign=middle;whiteSpace=wrap;rounded=0;fontSize=12;" vertex="1" parent="1">
          <mxGeometry x="60" y="140" width="260" height="20" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-line1" value="" style="line;strokeWidth=2;html=1;" vertex="1" parent="1">
          <mxGeometry x="60" y="175" width="260" height="10" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-color1" value="Person Hierarchy" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;fontSize=11;" vertex="1" parent="1">
          <mxGeometry x="60" y="190" width="120" height="25" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-color2" value="Business Entities" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;fontSize=11;" vertex="1" parent="1">
          <mxGeometry x="60" y="220" width="120" height="25" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-color3" value="Employee Types" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;fontSize=11;" vertex="1" parent="1">
          <mxGeometry x="60" y="250" width="120" height="25" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-color4" value="Payment System" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#ffe6cc;strokeColor=#d79b00;fontSize=11;" vertex="1" parent="1">
          <mxGeometry x="200" y="190" width="120" height="25" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-color5" value="External/Analytics" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;fontSize=11;" vertex="1" parent="1">
          <mxGeometry x="200" y="220" width="120" height="25" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-color6" value="Logistic" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;fontSize=11;" vertex="1" parent="1">
          <mxGeometry x="200" y="250" width="120" height="25" as="geometry" />
        </mxCell>
        
        <mxCell id="legend-note" value="Total: 20 Classes | All UCDs Covered" style="text;html=1;strokeColor=none;fillColor=none;align=center;verticalAlign=middle;whiteSpace=wrap;rounded=0;fontStyle=2;fontSize=10;" vertex="1" parent="1">
          <mxGeometry x="60" y="285" width="260" height="25" as="geometry" />
        </mxCell>

      </root>
    </mxGraphModel>
  </diagram>
</mxfile>