## Project Overview

This project is a demonstration and test implementation using Hexagonal Architecture (also known as Ports and Adapters), Clean Code principles, and Domain-Driven Design (DDD).

## Technical Assumptions

- All dates/times stored in the `prices` table are in UTC (no timezone information is stored).
- Price intervals for the same brand/product/priority may overlap because H2 does not support exclusion constraints or interval-based uniqueness. This means the database cannot enforce that prices with the same brand, product, and priority do not overlap in time.
- The rule about non-overlapping prices is treated as a domain assumption and is validated at the application level. The use of `LIMIT 2` in queries is a defensive measure to detect possible violations.
- Indexes are created to optimize queries by brand, product, and date range, avoiding full table scans.

If you have questions about any specific assumption or need to adapt the implementation to different requirements, please refer to the code comments or contact the project maintainer.