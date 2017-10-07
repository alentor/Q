using System;
using System.Collections.Generic;

namespace Q.WebAPI.Core.DataStructures
{
    public class GetReportsResponse
    {
        public List<Report> Reports { get; set; }
        public Guid LastReportHash { get; set; }
    }
}