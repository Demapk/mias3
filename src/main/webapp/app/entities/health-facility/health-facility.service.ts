import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { HealthFacility } from './health-facility.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<HealthFacility>;

@Injectable()
export class HealthFacilityService {

    private resourceUrl =  SERVER_API_URL + 'api/health-facilities';

    constructor(private http: HttpClient) { }

    create(healthFacility: HealthFacility): Observable<EntityResponseType> {
        const copy = this.convert(healthFacility);
        return this.http.post<HealthFacility>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(healthFacility: HealthFacility): Observable<EntityResponseType> {
        const copy = this.convert(healthFacility);
        return this.http.put<HealthFacility>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<HealthFacility>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<HealthFacility[]>> {
        const options = createRequestOption(req);
        return this.http.get<HealthFacility[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HealthFacility[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: HealthFacility = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<HealthFacility[]>): HttpResponse<HealthFacility[]> {
        const jsonResponse: HealthFacility[] = res.body;
        const body: HealthFacility[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to HealthFacility.
     */
    private convertItemFromServer(healthFacility: HealthFacility): HealthFacility {
        const copy: HealthFacility = Object.assign({}, healthFacility);
        return copy;
    }

    /**
     * Convert a HealthFacility to a JSON which can be sent to the server.
     */
    private convert(healthFacility: HealthFacility): HealthFacility {
        const copy: HealthFacility = Object.assign({}, healthFacility);
        return copy;
    }
}
