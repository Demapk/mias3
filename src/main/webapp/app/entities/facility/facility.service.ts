import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Facility } from './facility.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Facility>;

@Injectable()
export class FacilityService {

    private resourceUrl =  SERVER_API_URL + 'api/facilities';

    constructor(private http: HttpClient) { }

    create(facility: Facility): Observable<EntityResponseType> {
        const copy = this.convert(facility);
        return this.http.post<Facility>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(facility: Facility): Observable<EntityResponseType> {
        const copy = this.convert(facility);
        return this.http.put<Facility>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Facility>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Facility[]>> {
        const options = createRequestOption(req);
        return this.http.get<Facility[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Facility[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Facility = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Facility[]>): HttpResponse<Facility[]> {
        const jsonResponse: Facility[] = res.body;
        const body: Facility[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Facility.
     */
    private convertItemFromServer(facility: Facility): Facility {
        const copy: Facility = Object.assign({}, facility);
        return copy;
    }

    /**
     * Convert a Facility to a JSON which can be sent to the server.
     */
    private convert(facility: Facility): Facility {
        const copy: Facility = Object.assign({}, facility);
        return copy;
    }
}
