using System;
using Microsoft.AspNetCore.Mvc;
using Q.WebAPI.Core;
using Q.WebAPI.Core.DataStructures;

namespace Q.WebAPI.Controllers {
    [Route("api/[controller]")]
    public class ReportController: Controller {
        // GET api/Report
        // E.G. http://localhost:2533/api/report?location.latitude=123&location.longitude=456&Queue=7890
        [HttpGet]
        public string Get (Report report){
            report.DateTime = DateTime.Now;
            StaticData.Reports.Add(report);
            StaticData.LastReportHash = Guid.NewGuid();
            return StaticData.LastReportHash.ToString();
        }
    }
}