/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MiasTestModule } from '../../../test.module';
import { FacilityComponent } from '../../../../../../main/webapp/app/entities/facility/facility.component';
import { FacilityService } from '../../../../../../main/webapp/app/entities/facility/facility.service';
import { Facility } from '../../../../../../main/webapp/app/entities/facility/facility.model';

describe('Component Tests', () => {

    describe('Facility Management Component', () => {
        let comp: FacilityComponent;
        let fixture: ComponentFixture<FacilityComponent>;
        let service: FacilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [FacilityComponent],
                providers: [
                    FacilityService
                ]
            })
            .overrideTemplate(FacilityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacilityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Facility(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.facilities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
